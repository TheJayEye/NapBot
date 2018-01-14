package com.tinytimrob.ppse.napbot.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.tinytimrob.ppse.napbot.CommonPolyStuff;
import com.tinytimrob.ppse.napbot.NapSchedule;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMemberList implements ICommand
{
	@Override
	public String[] getCommandName()
	{
		return new String[] { "memberlist" };
	}

	@Override
	public String getCommandHelpUsage()
	{
		return "memberlist";
	}

	@Override
	public String getCommandHelpDescription()
	{
		return "list everyone by schedule";
	}

	@Override
	public boolean hasPermission(User user)
	{
		return true;
	}

	@Override
	public boolean execute(User user, TextChannel channel, String command, List<String> parameters, Message message) throws Exception
	{
		LinkedHashMap<NapSchedule, ArrayList<String>> hm = new LinkedHashMap<NapSchedule, ArrayList<String>>();
		for (NapSchedule s : NapSchedule.values())
		{
			hm.put(s, new ArrayList<String>());
		}
		List<Member> mlist = channel.getGuild().getMembers();
		int memberCount = 0;
		for (Member m : mlist)
		{
			if (!m.getUser().isBot())
			{
				String en = m.getEffectiveName();
				NapSchedule s = CommonPolyStuff.determineScheduleFromMemberName(en);
				ArrayList<String> l = hm.get(s);
				String suf = " [" + s.name + "]";
				if (en.endsWith(suf))
				{
					en = en.substring(0, en.length() - suf.length());
				}
				l.add(en.replace("_", "\\_").replace("*", "\\*"));
				memberCount++;
			}
		}
		// make the message
		String currentMessage = "There are **" + memberCount + "** members on this server.\n";
		for (NapSchedule s : NapSchedule.values())
		{
			ArrayList<String> l = hm.get(s);
			Collections.sort(l, String.CASE_INSENSITIVE_ORDER);
			String MSG = "**" + s.name + "** (" + l.size() + "): " + StringUtils.join(l, ",\u001F");
			currentMessage = currentMessage + "\n---\r" + MSG;
		}
		int LENGTH_LIMIT = 1900;
		while (!currentMessage.isEmpty())
		{
			// now we have to split apart the current message to get the message to be sent. had to alter how this works due to UNKNOWN on the main server now being too long
			String splitMessage = currentMessage.substring(0, Math.min(currentMessage.length(), LENGTH_LIMIT));
			if (currentMessage.length() > LENGTH_LIMIT)
			{
				int cutoffPoint = splitMessage.lastIndexOf("\n");
				if (cutoffPoint == -1)
				{
					cutoffPoint = splitMessage.lastIndexOf("\u001F");
				}
				if (cutoffPoint == -1)
				{
					cutoffPoint = LENGTH_LIMIT;
				}
				splitMessage = currentMessage.substring(0, Math.min(currentMessage.length(), cutoffPoint));
			}
			currentMessage = (splitMessage.length() == currentMessage.length() ? "" : ".\n" + currentMessage.substring(splitMessage.length() + 1));
			if (!splitMessage.isEmpty())
			{
				channel.sendMessage(splitMessage.replaceAll("\u001F", " ").replaceAll("\r", "\n")).complete();
			}
		}
		return true;
	}
}
