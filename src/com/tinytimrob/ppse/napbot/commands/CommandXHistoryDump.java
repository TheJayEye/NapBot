package com.tinytimrob.ppse.napbot.commands;

import java.util.List;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandXHistoryDump implements ICommand
{
	@Override
	public String[] getCommandName()
	{
		return new String[] { "xhistorydump" };
	}

	@Override
	public boolean hasPermission(User user)
	{
		return user.getId().equals("147356941860077568"); // rob only
	}

	@Override
	public boolean execute(User user, TextChannel channel, String command, List<String> parameters, Message message) throws Exception
	{
		int count = 0;
		System.out.println("Retrieving messages... " + count);
		MessageHistory mhistory = channel.getHistory();
		while (true)
		{
			List<Message> mlist = mhistory.retrievePast(100).complete();
			count += mlist.size();
			Thread.sleep(50); // try not to annoy Hammer + Chisel too much
			if (mlist.isEmpty())
				break;
			System.out.println("Retrieving messages... " + count);
		}
		while (true)
		{
			List<Message> mlist = mhistory.retrieveFuture(100).complete();
			count += mlist.size();
			Thread.sleep(50); // try not to annoy Hammer + Chisel too much
			if (mlist.isEmpty())
				break;
			System.out.println("Retrieving messages... " + count);
		}
		List<Message> messages = mhistory.getRetrievedHistory();
		for (Message m : messages)
		{
			if (m.getRawContent().startsWith("+set ") || m.getRawContent().startsWith("+mset "))
			{
				System.out.println(m.getAuthor().getName() + " >> " + m.getCreationTime().toEpochSecond() + " >> " + m.getContent());
			}
		}
		channel.sendMessage("history dump complete").complete();
		return true;
	}

	@Override
	public String getCommandHelpUsage()
	{
		return "xhistorydump";
	}

	@Override
	public String getCommandHelpDescription()
	{
		return "rob's history dump tool";
	}
}
