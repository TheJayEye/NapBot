package com.tinytimrob.ppse.napbot.commands;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;
import com.tinytimrob.ppse.napbot.CommonPolyStuff;
import com.tinytimrob.ppse.napbot.NapBot;
import com.tinytimrob.ppse.napbot.NapBotDb;
import com.tinytimrob.ppse.napbot.NapSchedule;
import com.tinytimrob.ppse.napbot.NapScheduleVariant;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandSet implements ICommand
{
	Pattern NAPCHART_PATTERN = Pattern.compile("\\Qhttp\\Es?\\Q://napchart.com/\\E[a-zA-Z0-9]{5}");

	@Override
	public String[] getCommandName()
	{
		return new String[] { "set" };
	}

	@Override
	public boolean hasPermission(User user)
	{
		return true;
	}

	@Override
	public boolean execute(User user, TextChannel channel, String command, List<String> parameters, Message message) throws Exception
	{
		if (parameters.size() == 1)
		{
			String param = parameters.get(0);
			if (param.equals("none"))
			{
				String text = NapBotDb.removeNapchart(user, channel);
				if (!text.isEmpty())
				{
					channel.sendMessage(user.getAsMention() + " Your napchart has been removed.").complete();
				}
				else
				{
					channel.sendMessage(user.getAsMention() + " You did not have a napchart to remove!").complete();
				}
			}
			else if (this.NAPCHART_PATTERN.matcher(param).matches())
			{
				NapBotDb.setNapchart(user, param);
				channel.sendMessage(user.getAsMention() + " Your napchart has been saved.").complete();
			}
			else
			{
				Pair<NapSchedule, NapScheduleVariant> newSchedulePair = CommonPolyStuff.setSchedule(user, channel, param);
				if (newSchedulePair == null || newSchedulePair.getLeft() == null)
				{
					channel.sendMessage("I don't know what you mean by `" + NapBot.CONFIGURATION.messagePrefix + "set " + param + "`. `" + param + "` doesn't seem to be a valid sleep schedule OR a valid napchart link.\n\n**If you are trying to change your sleep schedule:**\nTry specifying one of the following schedules: " + NapSchedule.getScheduleList() + ". You may also specify a schedule variant by using a dash separator, e.g. `DC1-extended` (supported variants are `shortened` `extended` `flipped` `modified` `mutated` and `recovery`).\n\n**If you are trying to change your napchart:**\nCheck to make sure the link you pasted is not malformed.\n\nFor more detailed usage instructions, type `" + NapBot.CONFIGURATION.messagePrefix + "help`.").complete();
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
					String rnc = NapBotDb.removeNapchart(user, channel);
					channel.sendMessage(user.getAsMention() + " Your sleep schedule has been set to " + sstr + "." + rnc).complete();
				}
			}
			return true;
		}
		else if (parameters.size() == 2)
		{
			String schedule = parameters.get(0);
			String napchart = parameters.get(1);
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
					channel.sendMessage("`" + schedule + "` doesn't seem to be a valid sleep schedule.\n\nTry specifying one of the following sleep schedules: " + NapSchedule.getScheduleList() + ". You may also specify a schedule variant by using a dash separator, e.g. `DC1-extended` (supported variants are `shortened` `extended` `flipped` `modified` `mutated` and `recovery`).\n\nIf you're already on the correct schedule and just want to set your napchart, you can do that by omitting the schedule name like so:\n`" + NapBot.CONFIGURATION.messagePrefix + "set " + napchart + "`\n\nFor more detailed usage instructions, type `" + NapBot.CONFIGURATION.messagePrefix + "help`.").complete();
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
						String ret = NapBotDb.removeNapchart(user, channel);
						if (ret.isEmpty())
						{
							channel.sendMessage(user.getAsMention() + " Your sleep schedule has been set to " + sstr + ".").complete();
						}
						else
						{
							channel.sendMessage(user.getAsMention() + " Your sleep schedule has been set to " + sstr + " and your napchart has been removed.").complete();
						}
					}
					else
					{
						NapBotDb.setNapchart(user, napchart);
						channel.sendMessage(user.getAsMention() + " Your sleep schedule has been set to " + sstr + " and your napchart has been saved.").complete();
					}
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getCommandHelpUsage()
	{
		return "set [schedule-name] [napchart-link]";
	}

	@Override
	public String getCommandHelpDescription()
	{
		return "set your current sleep schedule and/or napchart link";
	}
}
