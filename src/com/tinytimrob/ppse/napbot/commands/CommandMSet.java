package com.tinytimrob.ppse.napbot.commands;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;
import com.tinytimrob.ppse.napbot.CommonPolyStuff;
import com.tinytimrob.ppse.napbot.NapBot;
import com.tinytimrob.ppse.napbot.NapSchedule;
import com.tinytimrob.ppse.napbot.NapScheduleVariant;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMSet implements ICommand
{
	Pattern NAPCHART_PATTERN = Pattern.compile("\\Qhttp\\Es?\\Q://napchart.com/\\E[a-zA-Z0-9]{5}");

	@Override
	public String[] getCommandName()
	{
		return new String[] { "mset" };
	}

	@Override
	public boolean hasPermission(User user)
	{
		return CommonPolyStuff.isUserModerator(user);
	}

	@Override
	public boolean execute(User moderator, TextChannel channel, String command, List<String> parameters, Message message) throws Exception
	{
		if (parameters.size() < 3)
		{
			return false;
		}

		String schedule = parameters.get(0);
		String napchart = parameters.get(1);
		String memberString = "";

		for (int i = 2; i < parameters.size(); i++)
		{
			memberString = memberString + parameters.get(i);
		}

		Member matchedMember = CommonPolyStuff.findMemberMatch(channel, memberString);

		if (matchedMember == null)
		{
			return true;
		}

		User user = matchedMember.getUser();

		if (!napchart.equals("none") && !this.NAPCHART_PATTERN.matcher(napchart).matches())
		{
			channel.sendMessage("`" + napchart + "` doesn't seem to be a valid napchart link. Check to make sure the link you pasted is not malformed.").complete();
			return true;
		}
		else
		{
			Pair<NapSchedule, NapScheduleVariant> newSchedulePair = CommonPolyStuff.setSchedule(user, channel, schedule);
			if (newSchedulePair == null || newSchedulePair.getLeft() == null)
			{
				channel.sendMessage("`" + schedule + "` doesn't seem to be a valid sleep schedule.\n\nTry specifying one of the following sleep schedules: " + NapSchedule.getScheduleList() + ". You may also specify a schedule variant by using a dash separator, e.g. `DC1-extended` (supported variants are `shortened` `extended` `flipped` `modified` and `mutated`).\n\nFor more detailed usage instructions, type `" + NapBot.CONFIGURATION.messagePrefix + "help` or `" + NapBot.CONFIGURATION.messagePrefix + "mhelp`.").complete();
				return true;
			}
			else
			{
				NapSchedule newSchedule = newSchedulePair.getLeft();
				NapScheduleVariant variant = newSchedulePair.getRight();
				String sstr = newSchedule.name;
				if (variant != null)
				{
					sstr += "-" + variant.name().toLowerCase(Locale.ENGLISH);
				}

				if (napchart.equals("none"))
				{
					String ret = CommonPolyStuff.removeNapchart(user, channel);
					if (ret.isEmpty())
					{
						channel.sendMessage(moderator.getAsMention() + " The sleep schedule for **" + matchedMember.getEffectiveName() + "** has been set to " + sstr + ".").complete();
					}
					else
					{
						channel.sendMessage(moderator.getAsMention() + " The sleep schedule for **" + matchedMember.getEffectiveName() + "** has been set to " + sstr + " and their napchart has been removed.").complete();
					}
				}
				else
				{
					CommonPolyStuff.setNapchart(user, channel, napchart);
					channel.sendMessage(moderator.getAsMention() + " The sleep schedule for **" + matchedMember.getEffectiveName() + "** has been set to " + sstr + " and their napchart has been saved.").complete();
				}
			}
		}
		return true;
	}

	@Override
	public String getCommandHelpUsage()
	{
		return "set [schedule-name] [napchart-link] [username]";
	}

	@Override
	public String getCommandHelpDescription()
	{
		return "set your current sleep schedule and/or napchart link";
	}
}
