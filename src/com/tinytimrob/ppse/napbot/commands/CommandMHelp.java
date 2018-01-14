package com.tinytimrob.ppse.napbot.commands;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.tinytimrob.ppse.napbot.CommonPolyStuff;
import com.tinytimrob.ppse.napbot.NapBot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMHelp implements ICommand
{
	@Override
	public String[] getCommandName()
	{
		return new String[] { "mhelp", "mh", "m?" };
	}

	@Override
	public boolean hasPermission(User user)
	{
		return CommonPolyStuff.isUserModerator(user);
	}

	@Override
	public boolean execute(User user, TextChannel channel, String command, List<String> parameters, Message message) throws Exception
	{
		ArrayList<String> output = new ArrayList<String>();
		output.add("--- Nap God moderator help ---");
		output.add("");
		output.add("-----------------------------------------------");
		output.add("**To make the Nap God say something:** `" + NapBot.CONFIGURATION.messagePrefix + "say [message]`");
		output.add("**To set someone's schedule and napchart:** `" + NapBot.CONFIGURATION.messagePrefix + "mset [schedule-name] [napchart-link] [username]`. Only the standard schedules are supported. Use `none` in place of the napchart link if you want the user not to have a napchart (any existing chart they have will be removed).");
		output.add("**To set napchart timestamp:** `" + NapBot.CONFIGURATION.messagePrefix + "msettimestamp [username] [YYYY-MM-DD HH:MM:SS]`");
		output.add("-----------------------------------------------");
		channel.sendMessage(StringUtils.join(output, '\n')).complete();
		return true;
	}

	@Override
	public String getCommandHelpUsage()
	{
		return "mhelp";
	}

	@Override
	public String getCommandHelpDescription()
	{
		return "Show the moderator help";
	}
}
