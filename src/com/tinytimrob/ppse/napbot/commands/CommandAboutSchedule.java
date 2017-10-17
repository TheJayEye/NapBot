package com.tinytimrob.ppse.napbot.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.tinytimrob.common.CommonUtils;
import com.tinytimrob.ppse.napbot.CommonPolyStuff;
import com.tinytimrob.ppse.napbot.NapBot;
import com.tinytimrob.ppse.napbot.NapSchedule;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.MessageEmbedImpl;

public class CommandAboutSchedule implements ICommand
{
	final NapSchedule schedule;

	public CommandAboutSchedule(NapSchedule schedule)
	{
		this.schedule = schedule;
	}

	@Override
	public String[] getCommandName()
	{
		if (this.schedule == NapSchedule.MONO)
		{
			return new String[] { "Mono", "Monophasic" };
		}
		return new String[] { this.schedule.name };
	}

	@Override
	public boolean hasPermission(User user)
	{
		return true;
	}

	private String pluralize(int value, String none, String singular_pr, String singular_ps, String plural_pr, String plural_ps)
	{
		return value == 0 ? none : value == 1 ? singular_pr + value + singular_ps : plural_pr + value + plural_ps;
	}

	@Override
	public boolean execute(User user, TextChannel channel, String command, List<String> parameters, Message message) throws Exception
	{
		ArrayList<String> info = new ArrayList<String>();
		info.add("`" + this.schedule.name + "` (" + this.schedule.longName + ")"); // - <https://napchart.com/" + this.schedule.napchartID + "> - " + NapBot.CONFIGURATION.napchartUrlPrefix + this.schedule.napchartID);
		if (!this.schedule.experimental.isEmpty())
		{
			info.add("-----------------------------------------------");
			info.add("_" + this.schedule.experimental + "_");
			info.add("-----------------------------------------------");
		}
		info.add("- **Total sleep:** " + this.schedule.totalSleep);
		info.add("- **Identification:** " + this.schedule.identification);
		info.add("- **Specification:** " + this.schedule.specification);
		info.add("- **Mechanism:** " + this.schedule.mechanism);
		info.add("- **Adaptation difficulty:** " + this.schedule.difficulty);
		info.add("- **Ideal scheduling:** " + this.schedule.scheduling);
		info.add("- **Popularity:** " + this.schedule.popularity);
		MessageBuilder b = new MessageBuilder();
		b.append(StringUtils.join(info, "\n"));
		MessageEmbedImpl embedimpl = new MessageEmbedImpl();
		embedimpl.setTitle("https://napchart.com/" + this.schedule.napchartID);
		embedimpl.setUrl("https://napchart.com/" + this.schedule.napchartID);
		embedimpl.setImage(new ImageInfo(NapBot.CONFIGURATION.napchartUrlPrefix + this.schedule.napchartID + "?" + NapBot.RESYNC_ID, null, 560, 560));
		embedimpl.setFields(new ArrayList<MessageEmbed.Field>());
		b.setEmbed(embedimpl);
		channel.sendMessage(b.build()).complete();
		ArrayList<String> people_on_schedule = new ArrayList<String>();
		ArrayList<String> people_adapted = new ArrayList<String>();
		List<Member> mlist = channel.getGuild().getMembers();
		int attemptedcount = 0;
		int adaptedcount = 0;
		int membercount = 0;
		for (Member m : mlist)
		{
			if (!m.getUser().isBot())
			{
				membercount++;

				String en = m.getEffectiveName();
				NapSchedule s = CommonPolyStuff.determineScheduleFromMemberName(en);
				String suf = " [" + s.name + "]";
				// is this the user's current schedule?
				if (s == this.schedule)
				{
					if (en.endsWith(suf))
					{
						en = en.substring(0, en.length() - suf.length());
					}
					people_on_schedule.add(en.replace("_", "\\_").replace("*", "\\*"));
				}
				else if (en.contains(" [") && en.endsWith("]"))
				{
					en = en.substring(0, en.lastIndexOf(" ["));
				}
				// did this user attempt this schedule in the past?
				int tagcount = 0;
				for (Role role : m.getRoles())
				{
					if (role.getName().equals("Attempted-" + this.schedule.name))
					{
						attemptedcount++;
						tagcount++;
					}
					else if (role.getName().equals("Adapted-" + this.schedule.name))
					{
						people_adapted.add(en.replace("_", "\\_").replace("*", "\\*"));
						attemptedcount++;
						adaptedcount++;
						tagcount++;
					}
				}
				if (tagcount == 2)
				{
					attemptedcount--; // this user has both tags. a huge SIGH to whoever mistagged this person
				}
				else if (tagcount == 0 && s == this.schedule)
				{
					attemptedcount++; // this user has neither tag. a huge SIGH to whoever mistagged this person
				}
			}
		}
		Collections.sort(people_on_schedule, String.CASE_INSENSITIVE_ORDER);
		Collections.sort(people_adapted, String.CASE_INSENSITIVE_ORDER);
		StringBuilder builder = new StringBuilder();
		builder.append("---- Schedule Statistics ----");
		builder.append("\n\n");
		if (this.schedule != NapSchedule.MONO)
		{
			// Determine how many people attempted the schedule and so forth
			builder.append("Attempted: ");
			builder.append("**" + attemptedcount + " / " + membercount + "** (" + CommonUtils.formatPercentage(attemptedcount, membercount, 2) + ") ");
			//builder.append(attemptedcount == 1 ? "has" : "have");
			//builder.append(" attempted this schedule.");
			builder.append("\n\n");
		}
		if (!people_on_schedule.isEmpty())
		{
			builder.append("Currently on this schedule: ");
			builder.append("**" + people_on_schedule.size() + " / " + membercount + "** (" + CommonUtils.formatPercentage(people_on_schedule.size(), membercount, 2) + " of members, " + CommonUtils.formatPercentage(people_on_schedule.size(), attemptedcount, 2) + " of attempted)");
			//builder.append(people_on_schedule.size() == 1 ? " is" : " are");
			//builder.append(" currently on this schedule");
			builder.append(people_on_schedule.isEmpty() ? "" : ":\n");
			if (!people_on_schedule.isEmpty())
			{
				builder.append(StringUtils.join(people_on_schedule, ", "));
			}
			builder.append("\n\n");
		}
		if (this.schedule != NapSchedule.MONO)
		{
			builder.append("Adapted: ");
			builder.append("**" + people_adapted.size() + " / " + membercount + "** (" + CommonUtils.formatPercentage(people_adapted.size(), membercount, 2) + " of members, " + CommonUtils.formatPercentage(people_adapted.size(), attemptedcount, 2) + " of attempted)");
			//builder.append(people_adapted.size() == 1 ? " has" : " have");
			//builder.append(" successfully adapted to this schedule");
			builder.append(people_adapted.isEmpty() ? "" : ":\n");
			if (!people_adapted.isEmpty())
			{
				builder.append(StringUtils.join(people_adapted, ", "));
			}
		}
		channel.sendMessage(builder.toString()).complete();
		return true;
	}

	@Override
	public String getCommandHelpUsage()
	{
		return this.schedule.name;
	}

	@Override
	public String getCommandHelpDescription()
	{
		return "Display info about " + this.schedule.longName;
	}
}
