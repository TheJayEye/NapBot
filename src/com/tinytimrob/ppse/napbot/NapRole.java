package com.tinytimrob.ppse.napbot;

public enum NapRole
{
	MONOPHASIC("Monophasic", "Non-polyphasic schedules"), //
	BIPHASIC("Biphasic", "Biphasic schedules"), //
	EVERYMAN("Everyman", "Everyman schedules"), //
	DUAL_CORE("Dual Core", "Dual core schedules"), //
	TRI_CORE("Tri Core", "Tri core schedules"), //
	EXPERIMENTAL("Experimental", "Experimental/Unproven schedules"), //
	SUPERHUMAN("Superhumans", "Nap-only schedules");

	public final String name;
	public final String helpName;

	NapRole(String name, String helpName)
	{
		this.name = name;
		this.helpName = helpName;
	}
}
