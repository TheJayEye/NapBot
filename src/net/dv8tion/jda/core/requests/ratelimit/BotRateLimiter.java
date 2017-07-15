/*
 *     Copyright 2015-2017 Austin Keener & Michael Ritter & Florian Spie�
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dv8tion.jda.core.requests.ratelimit;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONObject;
import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import net.dv8tion.jda.core.entities.impl.JDAImpl;
import net.dv8tion.jda.core.events.ExceptionEvent;
import net.dv8tion.jda.core.requests.RateLimiter;
import net.dv8tion.jda.core.requests.Request;
import net.dv8tion.jda.core.requests.Requester;
import net.dv8tion.jda.core.requests.Route.CompiledRoute;
import net.dv8tion.jda.core.requests.Route.RateLimit;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BotRateLimiter extends RateLimiter
{
	volatile Long timeOffset = null;
	volatile AtomicLong globalCooldown = new AtomicLong(Long.MIN_VALUE);

	public BotRateLimiter(Requester requester, int poolSize)
	{
		super(requester, poolSize);
	}

	@Override
	public Long getRateLimit(CompiledRoute route)
	{
		Bucket bucket = this.getBucket(route);
		synchronized (bucket)
		{
			return bucket.getRateLimit();
		}
	}

	@Override
	protected void queueRequest(Request request)
	{
		if (this.isShutdown)
			throw new RejectedExecutionException("Cannot queue a request after shutdown");
		Bucket bucket = this.getBucket(request.getRoute());
		synchronized (bucket)
		{
			bucket.addToQueue(request);
		}
	}

	@Override
	protected Long handleResponse(CompiledRoute route, HttpResponse<String> response)
	{
		Bucket bucket = this.getBucket(route);
		synchronized (bucket)
		{
			Headers headers = response.getHeaders();
			int code = response.getStatus();
			if (this.timeOffset == null)
				this.setTimeOffset(headers);

			if (code == 429)
			{
				String global = headers.getFirst("X-RateLimit-Global");
				String retry = headers.getFirst("Retry-After");
				if (retry == null || retry.isEmpty())
				{
					JSONObject limitObj = new JSONObject(response.getBody());
					retry = limitObj.get("retry_after").toString();
				}
				long retryAfter = Long.parseLong(retry);
				if (!Boolean.parseBoolean(global)) //Not global ratelimit
				{
					this.updateBucket(bucket, headers);
				}
				else
				{
					//If it is global, lock down the threads.
					this.globalCooldown.set(this.getNow() + retryAfter);
				}

				return retryAfter;
			}
			else
			{
				this.updateBucket(bucket, headers);
				return null;
			}
		}

	}

	private Bucket getBucket(CompiledRoute route)
	{
		String rateLimitRoute = route.getRatelimitRoute();
		Bucket bucket = (Bucket) this.buckets.get(rateLimitRoute);
		if (bucket == null)
		{
			synchronized (this.buckets)
			{
				bucket = (Bucket) this.buckets.get(rateLimitRoute);
				if (bucket == null)
				{
					bucket = new Bucket(rateLimitRoute, route.getBaseRoute().getRatelimit());
					this.buckets.put(rateLimitRoute, bucket);
				}
			}
		}
		return bucket;
	}

	public long getNow()
	{
		return System.currentTimeMillis() + this.getTimeOffset();
	}

	public long getTimeOffset()
	{
		return this.timeOffset == null ? 0 : this.timeOffset;
	}

	private void setTimeOffset(Headers headers)
	{
		//Store as soon as possible to get the most accurate time difference;
		long time = System.currentTimeMillis();
		if (this.timeOffset == null)
		{
			//Get the date header provided by Discord.
			//Format:  "date" : "Fri, 16 Sep 2016 05:49:36 GMT"
			String date = headers.getFirst("Date");
			if (date != null)
			{
				OffsetDateTime tDate = OffsetDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
				long lDate = tDate.toInstant().toEpochMilli(); //We want to work in milliseconds, not seconds
				this.timeOffset = lDate - time; //Get offset in milliseconds.
			}
		}
	}

	private void updateBucket(Bucket bucket, Headers headers)
	{
		try
		{
			if (bucket.hasRatelimit()) // Check if there's a hardcoded rate limit 
			{
				bucket.resetTime = this.getNow() + bucket.getRatelimit().getResetTime();
				//routeUsageLimit provided by the ratelimit object already in the bucket.
			}
			else
			{
				bucket.resetTime = Long.parseLong(headers.getFirst("X-RateLimit-Reset")) * 1000; //Seconds to milliseconds
				bucket.routeUsageLimit = Integer.parseInt(headers.getFirst("X-RateLimit-Limit"));
			}

			//Currently, we check the remaining amount even for hardcoded ratelimits just to further respect Discord
			// however, if there should ever be a case where Discord informs that the remaining is less than what
			// it actually is and we add a custom ratelimit to handle that, we will need to instead move this to the
			// above else statement and add a bucket.routeUsageRemaining-- decrement to the above if body.
			//An example of this statement needing to be moved would be if the custom ratelimit reset time interval is
			// equal to or greater than 1000ms, and the remaining count provided by discord is less than the ACTUAL
			// amount that their systems allow in such a way that isn't a bug.
			//The custom ratelimit system is primarily for ratelimits that can't be properly represented by Discord's
			// header system due to their headers only supporting accuracy to the second. The custom ratelimit system
			// allows for hardcoded ratelimits that allow accuracy to the millisecond which is important for some
			// ratelimits like Reactions which is 1/0.25s, but discord reports the ratelimit as 1/1s with headers.
			bucket.routeUsageRemaining = Integer.parseInt(headers.getFirst("X-RateLimit-Remaining"));
		}
		catch (NumberFormatException ex)
		{
			if (!bucket.getRoute().equals("gateway") && !bucket.getRoute().equals("users/@me"))
			{
				Requester.LOG.debug("Encountered issue with headers when updating a bucket" + "\nRoute: " + bucket.getRoute() + "\nHeaders: " + headers);
			}

		}
	}

	private class Bucket implements IBucket, Runnable
	{
		final String route;
		final RateLimit rateLimit;
		volatile long resetTime = 0;
		volatile int routeUsageRemaining = 1; //These are default values to only allow 1 request until we have properly
		volatile int routeUsageLimit = 1; // ratelimit information.
		volatile ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<>();

		public Bucket(String route, RateLimit rateLimit)
		{
			this.route = route;
			this.rateLimit = rateLimit;
			if (rateLimit != null)
			{
				this.routeUsageRemaining = rateLimit.getUsageLimit();
				this.routeUsageLimit = rateLimit.getUsageLimit();
			}
		}

		void addToQueue(Request request)
		{
			this.requests.add(request);
			this.submitForProcessing();
		}

		void submitForProcessing()
		{
			synchronized (BotRateLimiter.this.submittedBuckets)
			{
				if (!BotRateLimiter.this.submittedBuckets.contains(this))
				{
					Long delay = this.getRateLimit();
					if (delay == null)
						delay = 0L;

					BotRateLimiter.this.pool.schedule(this, delay, TimeUnit.MILLISECONDS);
					BotRateLimiter.this.submittedBuckets.add(this);
				}
			}
		}

		Long getRateLimit()
		{
			long gCooldown = BotRateLimiter.this.globalCooldown.get();
			if (gCooldown != Long.MIN_VALUE) //Are we on global cooldown?
			{
				long now = BotRateLimiter.this.getNow();
				if (now > gCooldown) //Verify that we should still be on cooldown.
				{
					BotRateLimiter.this.globalCooldown.set(Long.MIN_VALUE); //If we are done cooling down, reset the globalCooldown and continue.
				}
				else
				{
					return gCooldown - now; //If we should still be on cooldown, return when we can go again.
				}
			}
			if (this.routeUsageRemaining <= 0)
			{
				if (BotRateLimiter.this.getNow() > this.resetTime)
				{
					this.routeUsageRemaining = this.routeUsageLimit;
					this.resetTime = 0;
				}
			}
			if (this.routeUsageRemaining > 0)
				return null;
			else
				return this.resetTime - BotRateLimiter.this.getNow();
		}

		@Override
		public boolean equals(Object o)
		{
			if (!(o instanceof Bucket))
				return false;

			Bucket oBucket = (Bucket) o;
			return this.route.equals(oBucket.route);
		}

		@Override
		public int hashCode()
		{
			return this.route.hashCode();
		}

		@Override
		public void run()
		{
			try
			{
				synchronized (this.requests)
				{
					for (Iterator<Request> it = this.requests.iterator(); it.hasNext();)
					{
						Request request = null;
						try
						{
							request = it.next();
							Long retryAfter = BotRateLimiter.this.requester.execute(request);
							if (retryAfter != null)
							{
								break;
							}
							else
							{
								it.remove();
							}
						}
						catch (Throwable t)
						{
							Requester.LOG.fatal("Requester system encountered an internal error");
							Requester.LOG.log(t);
							it.remove();
							if (request != null)
								request.onFailure(t);
						}
					}

					synchronized (BotRateLimiter.this.submittedBuckets)
					{
						BotRateLimiter.this.submittedBuckets.remove(this);
						if (!this.requests.isEmpty())
						{
							try
							{
								this.submitForProcessing();
							}
							catch (RejectedExecutionException e)
							{
								Requester.LOG.debug("Caught RejectedExecutionException when re-queuing a ratelimited request. The requester is probably shutdown, thus, this can be ignored.");
							}
						}
					}
				}
			}
			catch (Throwable err)
			{
				Requester.LOG.fatal("Requester system encountered an internal error from beyond the synchronized execution blocks. NOT GOOD!");
				Requester.LOG.log(err);
				if (err instanceof Error)
				{
					JDAImpl api = BotRateLimiter.this.requester.getJDA();
					api.getEventManager().handle(new ExceptionEvent(api, err, true));
				}
			}
		}

		@Override
		public RateLimit getRatelimit()
		{
			return this.rateLimit;
		}

		@Override
		public String getRoute()
		{
			return this.route;
		}

		@Override
		public Queue<Request> getRequests()
		{
			return this.requests;
		}
	}
}