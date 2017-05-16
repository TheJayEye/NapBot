package com.tinytimrob.ppse.napbot.commands;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.tinytimrob.ppse.napbot.NapBot;
import com.tinytimrob.ppse.napbot.NapRole;
import com.tinytimrob.ppse.napbot.NapSchedule;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandHelp implements ICommand
{
	@Override
	public String[] getCommandName()
	{
		return new String[] { "help", "h", "?" };
	}

	@Override
	public boolean hasPermission(User user)
	{
		return true;
	}

	@Override
	public boolean execute(User user, TextChannel channel, String command, List<String> parameters) throws Exception
	{
		ArrayList<String> output = new ArrayList<String>();
		output.add("--- Nap God help ---");
		output.add("");
		output.add("Nap God can be used to look up information about common sleep schedules, change your displayed sleep schedule and group, and to save and look up napcharts.");
		output.add("-----------------------------------------------");
		output.add("**Schedule List**");
		output.add("To view information about a polyphasic sleep schedule, type `+` followed by the schedule name. For example, `+DC1` will display information about DC1.");
		output.add("");
		for (NapRole role : NapRole.values())
		{
			if (role.helpName != null)
			{
				String x = "" + role.helpName + ":";
				for (NapSchedule schedule : NapSchedule.values())
				{
					if (schedule.role == role && !schedule.totalSleep.isEmpty())
					{
						x = x + " `" + schedule.name + "`";
					}
				}
				output.add(x);
			}
		}
		output.add("");
		output.add("Once you have chosen your sleep schedule you can use the `+set` command to tag yourself with it and move yourself to the matching role. You can also use `+set` to set a napchart against your account (created on <https://napchart.com/> or with the `+create` command) which others can then look at. Further details found below.");
		output.add("");
		output.add("If you are new to polyphasic sleeping and are not sure about which sleep schedule to try, feel free to ask for help and advice in the <#249219704655183876> channel.");
		output.add("-----------------------------------------------");
		channel.sendMessage(StringUtils.join(output, '\n')).complete();
		output = new ArrayList<String>();
		output.add("-");
		output.add("-----------------------------------------------");
		output.add("**To set your sleep schedule:** Type `" + NapBot.CONFIGURATION.messagePrefix + "set` followed by the schedule name. For example, if you wanted to change your schedule to DC1, you would type `" + NapBot.CONFIGURATION.messagePrefix + "set DC1`. All of the schedules listed above are supported. If you don't see your schedule listed, or you are doing some variant (e.g. a modified/extended/underage version), select the closest option and then correct your nickname by hand.");
		output.add("-----------------------------------------------");
		output.add("**To set your napchart:** Type `" + NapBot.CONFIGURATION.messagePrefix + "set` followed by the napchart link. For example, `" + NapBot.CONFIGURATION.messagePrefix + "set https://napchart.com/ro1mi`");
		output.add("-----------------------------------------------");
		output.add("**To set both at the same time:** Just specify both. For example, `" + NapBot.CONFIGURATION.messagePrefix + "set DC1 https://napchart.com/ro1mi`");
		output.add("-----------------------------------------------");
		output.add("**To remove your sleep schedule:** Type `" + NapBot.CONFIGURATION.messagePrefix + "set monophasic`. Your tag and user role will be removed.");
		output.add("-----------------------------------------------");
		output.add("**To look up your own napchart:** Type `" + NapBot.CONFIGURATION.messagePrefix + "get`.");
		output.add("-----------------------------------------------");
		output.add("**To look up someone else's napchart:** Type `" + NapBot.CONFIGURATION.messagePrefix + "get` followed by the name of the user. Any of the following name formats will work: `" + NapBot.CONFIGURATION.messagePrefix + "get Tinytimrob`, `" + NapBot.CONFIGURATION.messagePrefix + "get Tinytimrob#1956`, `" + NapBot.CONFIGURATION.messagePrefix + "get @Tinytimrob`. Mentions should be avoided though as these will ping the user in question.");
		output.add("-----------------------------------------------");
		output.add("**To create a new napchart:** Type `+create` followed by a series of time ranges. For example, `" + NapBot.CONFIGURATION.messagePrefix + "create 03:00-05:00 08:00-08:20 14:00-14:20 21:00-23:00`. A napchart link will then be generated for you.");
		output.add("-----------------------------------------------");
		channel.sendMessage(StringUtils.join(output, '\n')).complete();
		return true;
	}

	@Override
	public String getCommandHelpUsage()
	{
		return "help";
	}

	@Override
	public String getCommandHelpDescription()
	{
		return "Show the help";
	}
}
