package com.tinytimrob.ppse.napbot.commands;

import java.util.List;
import com.tinytimrob.ppse.napbot.CommonPolyStuff;
import com.tinytimrob.ppse.napbot.NapSchedule;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandToggleWatchGroup implements ICommand
{
	@Override
	public String[] getCommandName()
	{
		return new String[] { "togglewatchgroup" };
	}

	@Override
	public String getCommandHelpUsage()
	{
		return "togglewatchgroup";
	}

	@Override
	public String getCommandHelpDescription()
	{
		return "toggle NMO watch group on or off";
	}

	@Override
	public boolean hasPermission(User user)
	{
		return true;
	}

	@Override
	public boolean execute(User user, TextChannel channel, String command, List<String> parameters, Message message) throws Exception
	{
		Member member = channel.getGuild().getMember(user);
		NapSchedule schedule = CommonPolyStuff.determineScheduleFromMemberName(member.getEffectiveName());
		List<Role> nmoRole = channel.getGuild().getRolesByName("NMO Watch Group", true);
		boolean inWatchGroup = false;
		for (Role role : member.getRoles())
		{
			if (nmoRole.contains(role))
			{
				inWatchGroup = true;
				break;
			}
		}
		if (inWatchGroup)
		{
			channel.getGuild().getController().removeRolesFromMember(member, nmoRole).complete();
			channel.sendMessage(member.getAsMention() + " You have been removed from the NMO Watch Group.").complete();
		}
		else if (schedule == null)
		{
			channel.sendMessage(member.getAsMention() + " You must set a sleep schedule before you can join the NMO Watch Group").complete();
		}
		else
		{
			channel.getGuild().getController().addRolesToMember(member, nmoRole).complete();
			channel.sendMessage(member.getAsMention() + " You have been added to the NMO Watch Group.").complete();
		}
		return true;
	}
}
