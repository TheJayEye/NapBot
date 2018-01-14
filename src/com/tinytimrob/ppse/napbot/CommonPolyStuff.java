package com.tinytimrob.ppse.napbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;

public class CommonPolyStuff
{
	public static boolean isUserModerator(User user)
	{
		return user.getId().equals("147356941860077568") || NapBot.CONFIGURATION.moderators.contains(user.getId());
	}

	public static Pair<NapSchedule, NapScheduleVariant> setSchedule(User user, TextChannel channel, String scheduleString)
	{
		String scheduleString_ = scheduleString.toLowerCase(Locale.ENGLISH);
		NapSchedule schedule = null;
		NapScheduleVariant variant = null;
		for (NapScheduleVariant variant_ : NapScheduleVariant.values())
		{
			String vlc = variant_.name().toLowerCase();
			if (scheduleString_.endsWith("-" + vlc))
			{
				variant = variant_;
				scheduleString_ = scheduleString_.substring(0, scheduleString_.length() - (vlc.length() + 1));
				break;
			}
			if (scheduleString_.startsWith(vlc + "-"))
			{
				variant = variant_;
				scheduleString_ = scheduleString_.substring(variant.name().length() + 1);
				break;
			}
		}
		for (NapSchedule schedule_ : NapSchedule.values())
		{
			if (schedule == NapSchedule.UNKNOWN)
			{
				continue;
			}
			if (schedule_.name.equalsIgnoreCase(scheduleString_))
			{
				schedule = schedule_;
				break;
			}
		}
		if (schedule == null) // for very lazy people allow some extra strings
		{
			switch (scheduleString_.toLowerCase())
			{
			case "monophasic":
				schedule = NapSchedule.MONO;
				break;
			case "tricore":
			case "tri-core":
			case "tri_core":
			case "prototype1":
			case "prototype-1":
			case "prototype_1":
				schedule = NapSchedule.TC1;
				break;
			case "u6":
				schedule = NapSchedule.UBERMAN;
				break;
			case "u12":
				schedule = NapSchedule.NAPTATION;
				break;
			default:
				break;
			}
		}
		if (schedule == null)
		{
			return null;
		}
		NapRole targetRole = variant == NapScheduleVariant.MUTATED ? NapRole.SUPERHUMAN : schedule.role;
		String subRoleCode = variant == NapScheduleVariant.MUTATED ? "Mutated" : schedule.name;

		ArrayList<String> napRoles = new ArrayList<String>();
		for (NapRole role : NapRole.values())
		{
			napRoles.add(role.name.toLowerCase(Locale.ENGLISH));
		}
		Member member = channel.getGuild().getMember(user);
		ArrayList<Role> rolesToAdd = new ArrayList<Role>();
		ArrayList<Role> rolesToRemove = new ArrayList<Role>();
		rolesToAdd.addAll(channel.getGuild().getRolesByName(targetRole.name, true));
		// determine whether this member needs attempted tag
		List<Role> rolesTheMemberHas = member.getRoles();
		boolean needsAttemptedRole = true;
		for (Role role : rolesTheMemberHas)
		{
			if (role.getName().equals("Adapted-" + subRoleCode))
			{
				needsAttemptedRole = false;
				break;
			}
		}
		if (needsAttemptedRole)
		{
			rolesToAdd.addAll(channel.getGuild().getRolesByName("Attempted-" + subRoleCode, true));
		}
		for (Role role : rolesTheMemberHas)
		{
			if (!rolesToAdd.contains(role) && napRoles.contains(role.getName().toLowerCase(Locale.ENGLISH)))
			{
				rolesToRemove.add(role);
			}
		}
		channel.getGuild().getController().modifyMemberRoles(member, rolesToAdd, rolesToRemove).complete();

		// update the nickname
		String s = member.getEffectiveName();
		if (s.contains(" ["))
		{
			s = s.substring(0, s.lastIndexOf(" ["));
		}
		s = s + " [" + schedule.name + (variant == null ? "" : "-" + variant.name().toLowerCase()) + "]";
		try
		{
			channel.getGuild().getController().setNickname(member, s).complete();
		}
		catch (PermissionException e)
		{
			// no permission to set nickname, FGS
			e.printStackTrace();
		}
		return Pair.of(schedule, variant);
	}

	public static Member findMemberMatch(TextChannel channel, String match)
	{
		List<Member> matchingMembers = new ArrayList<Member>();

		if (match.startsWith("<@") && match.contains(">"))
		{
			// based on @mention. why do some snowflakes start with ! and some not? wtf?
			String id = match.substring(match.startsWith("<@!") ? 3 : 2, match.indexOf(">"));
			Member member = channel.getGuild().getMemberById(id);
			if (member != null)
			{
				matchingMembers.add(member);
			}
		}
		else
		{
			// look up the user by name. sadly we can't use the built in "effective match" because of the sleep schedules being part of nickname
			// so we're going to have to do this the old fashioned way
			for (Member member : channel.getGuild().getMembers())
			{
				String s = member.getEffectiveName();
				if (s.contains("["))
				{
					s = s.substring(0, s.lastIndexOf("[")).trim();
				}
				if (s.equalsIgnoreCase(match))
				{
					matchingMembers.add(member);
				}
				else if (member.getUser().getName().equalsIgnoreCase(match))
				{
					matchingMembers.add(member);
				}
				else if ((member.getUser().getName() + "#" + member.getUser().getDiscriminator()).equalsIgnoreCase(match))
				{
					matchingMembers.add(member);
				}
			}
		}

		if (matchingMembers.isEmpty())
		{
			channel.sendMessage("I wasn't able to find anyone called `" + match + "` on the server.").complete();
			return null;
		}
		else if (matchingMembers.size() > 1)
		{
			ArrayList<String> output = new ArrayList<String>();
			output.add("Matched multiple users called `" + match + "`. Please choose one of the following:");
			for (Member x : matchingMembers)
			{
				output.add(x.getUser().getName() + "#" + x.getUser().getDiscriminator());
			}
			channel.sendMessage(StringUtils.join(output, '\n')).complete();
			return null;
		}
		else
		{
			return matchingMembers.get(0);
		}
	}

	public static NapSchedule determineScheduleFromMemberName(String name)
	{
		if (!name.contains("["))
		{
			return NapSchedule.UNKNOWN;
		}
		String a = name.substring(name.lastIndexOf("[") + 1);
		if (!a.contains("]"))
		{
			return NapSchedule.UNKNOWN;
		}
		a = a.substring(0, a.lastIndexOf("]")).trim();
		// okay so now 'a' should contain this person's schedule name...
		for (NapSchedule schedule : NapSchedule.values())
		{
			if (a.toUpperCase().contains(schedule.name.toUpperCase()))
			{
				return schedule;
			}
		}
		return NapSchedule.EXPERIMENTAL;
	}
}
