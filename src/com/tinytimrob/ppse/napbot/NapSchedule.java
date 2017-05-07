package com.tinytimrob.ppse.napbot;

public enum NapSchedule
{
	BIPHASIC("Biphasic", "Biphasic", NapRole.BIPHASIC, true), //
	DC1("DC1", "Dual Core 1", NapRole.DUAL_CORE, true), //
	DC2("DC2", "Dual Core 2", NapRole.DUAL_CORE, true), //
	DC3("DC3", "Dual Core 3", NapRole.DUAL_CORE, true), //
	DYMAXION("Dymaxion", "Dymaxion", NapRole.SUPERHUMAN, true), //
	E2("E2", "Everyman 2", NapRole.EVERYMAN, true), //
	E3("E3", "Everyman 3", NapRole.EVERYMAN, true), //
	E4("E4", "Everyman 4", NapRole.EVERYMAN, true), //
	PROTOTYPE_1("Prototype_1", "Prototype 1", NapRole.DUAL_CORE, true), //
	QUADPHASIC("Quadphasic", "Quadphasic", NapRole.DUAL_CORE, true), //
	SPAMAYL("SPAMAYL", "SPAMAYL", NapRole.SUPERHUMAN, true), //
	SEGMENTED("Segmented", "Segmented", NapRole.BIPHASIC, true), //
	SIESTA("Siesta", "Siesta", NapRole.BIPHASIC, true), //
	TRIPHASIC("Triphasic", "Triphasic", NapRole.DUAL_CORE, true), //
	UBERMAN("Uberman", "Uberman", NapRole.SUPERHUMAN, true), //
	RANDOM("Random", "Random", NapRole.RANDOM, false), //
	MONOPHASIC("Monophasic", "Monophasic", NapRole.MONOPHASIC, false);

	public final String name;
	public final String longName;
	public final NapRole role;
	public final boolean appendToNick;

	NapSchedule(String name, String longName, NapRole role, boolean appendToNick)
	{
		this.name = name;
		this.longName = longName;
		this.role = role;
		this.appendToNick = appendToNick;
	}
}