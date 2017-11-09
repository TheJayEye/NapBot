package com.tinytimrob.ppse.napbot;

public enum NapSchedule
{
	BIMAXION("Bimaxion", "Bimaxion", NapRole.DUAL_CORE,//
			"qamnm", //
			"4 hours", //
			"Dual Core sleep, Dymaxion naps", //
			"2 core sleeps of minimum length, 2 naps, and the late Dymaxion nap can contain both SWS and REM.", //
			"One core sleep before midnight, one around dawn, Dymaxion noon nap, and Dymaxion afternoon nap. Sleep stage division: Dusk core SWS, dawn core REM. Late nap can contain mixed sleep stages. More flexible than Dymaxion because there are 2 core sleeps to gain SWS and so 2 naps can be moved around to some extent.", //
			"Super hard", //
			"The whole schedule’s sleep distribution looks like Dymaxion's- one core before midnight, one core around dawn, one noon nap, one afternoon/evening nap.", //
			"Very low", //
			""), //
	DC1("DC1", "Dual Core 1", NapRole.DUAL_CORE,//
			"ugn4g", //
			"5 hours 20 minutes", //
			"Dual Core sleep", //
			"2 core sleeps, 1 nap", //
			"One core sleep around dusk, one core sleep around dawn, one nap around noon. Sleep stage division: Dusk core with mostly SWS and dawn core mostly REM.", //
			"Medium", //
			"Dusk core around 21:00 to 22:00, dawn core is free to place till 07:00 or 08:00, REM nap around noon or early afternoon", //
			"Rather high, most popular of Dual Core sleep.", //
			""), //
	DC2("DC2", "Dual Core 2", NapRole.DUAL_CORE,//
			"rxpjl", //
			"4 hours 40 minutes", //
			"Dual Core sleep", //
			"2 core sleeps, 2 naps", //
			"One core sleep around dusk, one core sleep around 02:00ish, one dawn nap and one nap around noon. Sleep stage division: Dusk core SWS, night core REM.", //
			"Hard", //
			"First core around 21:00, second core around 02:00, dawn nap before work, noon-early-afternoon nap", //
			"Very low", //
			""), //
	DC3("DC3", "Dual Core 3", NapRole.DUAL_CORE,//
			"arx4r", //
			"4 hours", //
			"Dual Core sleep", //
			"2 core sleeps, 3 naps", //
			"One core sleep before midnight, one after midnight, dawn nap, noon nap, and afternoon nap. Sleep stage division: Dusk core SWS, night core REM.", //
			"Very hard", //
			"3-hour wake between 2 cores, dawn nap, noon nap and afternoon nap", //
			"Very low", //
			""), //
	DC4("DC4", "Dual Core 4", NapRole.DUAL_CORE,//
			"qtny6", //
			"4 hours 20 minutes", //
			"Dual Core sleep", //
			"2 core sleeps, 4 naps", //
			"Transitional variant of DC3 with 4 naps that follows Uberman rhythm. One core sleep before midnight, one after midnight, dawn nap, mid-morning nap, mid-afternoon nap, and evening nap. Sleep stage division: First core SWS, second core mixed, naps REM.", //
			"Very hard", //
			"Sleeps are scheduled to start every 4 hours which resembles Uberman. Transition step to E5 or Uberman and also useful as a fallback from E5 or Uberman should adaptation fail.", //
			"Virtually non-existent, generally inferior to DC3 unless keeping Uberman rhythm is useful (the extra nap leads to slower sleep onset)", //
			""), //
	DYMAXION("Dymaxion", "Dymaxion", NapRole.SUPERHUMAN,//
			"zgqtz", //
			"2 hours", //
			"Ultrashort naps", //
			"4 naps of 30 minutes", //
			"4 naps per day, equidistantly spread throughout the day. Circadian spots include: midnight nap, dawn nap, noon nap, and evening nap. Mixed-stage nap(s) in all 4 naps, or possibly pure-REM or pure-SWS nap.", //
			"Almost impossible", //
			"Arranging naps to fit in 4 aforementioned circadian needs to rest", //
			"Decent", //
			"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	E1("E1", "Everyman 1", NapRole.BIPHASIC,//
			"30lbn", //
			"6 hours 20 minutes", //
			"Origin of Everyman sleep, biphasic sleep", //
			"1 long core sleep, 1 short nap", //
			"Two sleeps per day, main sleep through graveyard hours and a little rest around noon. Main sleep resembles monophasic sleep the most.", //
			"Very easy", //
			"Core at midnight, nap around noon", //
			"Most commonly used", //
			"**Not to be confused with Siesta.** The E1 schedule has 1 core + 1 nap, whereas the Siesta schedule has 1 long core + 1 short core"), //
	E2("E2", "Everyman 2", NapRole.EVERYMAN,//
			"fflmu", //
			"5 hours 10 minutes", //
			"Everyman sleep", //
			"1 long core sleep, 2 REM naps", //
			"1 core sleep (3 full cycles), 2 naps", //
			"Easy", //
			"Core close to midnight, nap before work, early-afternoon nap", //
			"High", //
			""), //
	E3("E3", "Everyman 3", NapRole.EVERYMAN,//
			"2b62f", //
			"4 hours", //
			"Everyman sleep", //
			"1 core, 3 naps", //
			"1 main sleep (2 full cycles), 3 REM naps", //
			"Decently hard", //
			"Core as close to dusk as possible to gain more SWS, one nap around 04:00, one nap post-dawn, one early-afternoon nap. All naps should contain REM and little light sleep only.", //
			"Very high. Most famous of all Everyman schedules.", //
			""), //
	E4("E4", "Everyman 4", NapRole.EVERYMAN,//
			"jkfzt", //
			"2 hours 50 minutes", //
			"Everyman sleep", //
			"1 min-length core (1 full cycle), 4 REM naps", //
			"1 core sleep before midnight to gain as much SWS as possible, 4 naps should only contain REM.", //
			"Extremely hard", //
			"Core before midnight, 2 naps before the day starts, noon nap, afternoon nap", //
			"Low", //
			"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	E5("E5", "Everyman 5", NapRole.EVERYMAN,//
			"coamr", //
			"3 hours 10 minutes", //
			"Everyman sleep", //
			"1 min-length core (1 full cycle), 5 naps", //
			"Transitional variant of E4 with 5 naps that follows Uberman rhythm. 1 core sleep before midnight to gain as much SWS as possible, graveyard nap, dawn nap, mid-morning nap, mid-afternoon nap, and evening nap. A combination of REM-only and mixed-stage naps is likely.", //
			"Extremely hard", //
			"Sleeps are scheduled to start every 4 hours which resembles Uberman. Transition step between DC4 and Uberman and also useful as a fallback from Uberman should adaptation fail.", //
			"Virtually non-existent, generally inferior to E4 unless keeping Uberman rhythm is useful (the extra nap leads to slower sleep onset)", //
			"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	NAPTATION("Naptation", "Naptation", NapRole.SUPERHUMAN,//
			"90uzo", //
			"Up to 4 hours (up to 12 20-min naps per day)", //
			"Ultrashort naps", //
			"Multiple short naps, naptation", //
			"Transitional schedule used by some people to adapt to 20-minute naps before moving to other schedules such as SPAMAYL or Uberman. 1-hour BRAC, napping at the end of every other BRAC. As adaptation progresses naps can slowly be removed.", //
			"Decently hard", //
			"Nap once every 1.7 hours", //
			"Rising", //
			""), //
	SPAMAYL("SPAMAYL", "Sleep Polyphasically As Much As You Like", NapRole.SUPERHUMAN,//
			"yh1pp", //
			"Undefined, but on average 2.5 hours (7-8 20-min naps per day)", //
			"Ultrashort naps", //
			"Multiple short naps, naptation", //
			"No rhythm, no BRAC concepts, there usually have to have more than 6 20-minute naps per day average. Stocking up sleep in a day then having few or even no naps the next day is also possible. Strong reliance on the ability to detect when the need to rest is, to get quality REM naps. At least 1 SWS nap, and some other naps can be mixed-stages nap. SPAMAYL can be stabilized and formed into a rhythm if one has a stable work/schedule everyday (set nap times, like 2-hour rhythm, napping once every 1.7 hours at night for example).", //
			"Super hard", //
			"Most naps should be spread during graveyard hours to avoid the need to nap during the day with work, social life, etc", //
			"Rather low", //
			"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	SEGMENTED("Segmented", "Segmented", NapRole.BIPHASIC,//
			"k0mot", //
			"7 hours", //
			"Origin of Dual Core sleep, biphasic sleep", //
			"2 core sleeps, with a short gap between them", //
			"Two sleeps per day, one sleep in the first half of the night (gives mostly SWS), another sleep in the second half of the night (gives mostly REM), 2-hour wake between 2 cores to trigger sleep stage division between 2 cores", //
			"Medium easy", //
			"1 core around 21:00, should not be later than 22:00ish, 1 core till morning", //
			"Rather high", //
			""), //
	SIESTA("Siesta", "Siesta", NapRole.BIPHASIC,//
			"e72xy", //
			"6 hours 30 minutes", //
			"Biphasic sleep", //
			"1 long core + 1 short core, placed roughly opposite each other", //
			"Two sleeps per day. Main sleep through most of the night with a long rest of 1 full cycle around noon", //
			"Extremely easy", //
			"Core around midnight, siesta at noon", //
			"Europe, generally inferior to E1", //
			"**Not to be confused with E1.** The E1 schedule has 1 core + 1 nap, whereas the Siesta schedule has 1 long core + 1 short core"), //
	TESLA("Tesla", "Tesla", NapRole.SUPERHUMAN,//
			"s2x5u", //
			"1 hour 20 minutes", //
			"Ultrashort naps", //
			"4 naps of 20 minutes", //
			"Variant of Dymaxion with 20 minute naps instead of 30 minute naps. 4 naps per day, equidistantly spread throughout the day. Circadian spots include: midnight nap, dawn nap, noon nap, and evening nap. Mixed-stage nap(s) in all 4 naps, or possibly pure-REM or pure-SWS nap.", //
			"Almost impossible", //
			"Arranging naps to fit in 4 aforementioned circadian needs to rest", //
			"Very low", //
			"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	TC1("TC1", "Tri Core 1", NapRole.TRI_CORE,//
			"tocl4", //
			"4 hours 50 minutes", //
			"Tri core sleep", //
			"3 core sleeps of minimum length, 1 nap", //
			"Sleep stage division: 1 SWS core, 2 REM core and 1 REM nap. 3-hour rhythm to line up with sleep-wake cycle length of each core sleep. Predicted to be more flexible than Dual Core sleep schedules because the core component of Tri Core consists of 3 cores to be shifted quite comfortably once adapted. ", //
			"Unknown", //
			"3 core sleeps are concentrated during the night to boost alertness for the whole day, so only one small nap in the afternoon is needed. 3-hour rhythm among each core sleep is recommended. Distance of wake time among each core sleep could be shortened if possible, but should not be less than 2 hours. Such a schedule will include a dusk core, night core, and dawn core.", //
			"Very low", //
			""), //
	TC2("TC2", "Tri Core 2", NapRole.TRI_CORE,//
			"43swa", //
			"5 hours 10 minutes", //
			"Tri core sleep", //
			"3 core sleeps of minimum length, 2 naps", //
			"Sleep stage division: 1 SWS core, 2 REM core and 2 REM naps", //
			"Unknown", //
			"Evening core, graveyard core, dawn core and 2 daytime naps", //
			"Very low", //
			""), //
	TRIMAXION("Trimaxion", "Trimaxion", NapRole.EVERYMAN,//
			"awkwb", //
			"3 hours", //
			"Everyman sleep, Dymaxion naps", //
			"1 core sleep of minimum length (one full cycle), 3 Dymaxion naps", //
			"4 sleeps per day. Core sleep before midnight for most SWS, naps for REM/mixed stages. Circadian spots include: Core sleep around dusk, dawn nap, noon nap, and evening nap. Mixed-stage nap(s) in all 3 naps, or possibly pure-REM or pure-SWS nap. More flexible than Dymaxion because of having a core sleep, so after adaptation 3 naps can be moved around when convenient.", //
			"Extremely hard", //
			"Distribution of sleeps resembles Dymaxion's and Bimaxion's. Transition step to Dymaxion and also as a fallback from Dymaxion should adaptation fail.", //
			"Virtually non-existent", //
			"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	TRIPHASIC("Triphasic", "Triphasic", NapRole.TRI_CORE,//
			"8z46u", //
			"4 hours 30 minutes", //
			"Tri core sleep", //
			"3 cores of minimum length", //
			"One core sleep before midnight, one around dawn, and one around noon. Sleep stage separation of SWS and REM into 2 cores of the night, like Dual Core sleep. Long siesta to balance out the schedule.", //
			"Varies", //
			"One core sleep around 21:00, one around 05:30, one around noon", //
			"Low", //
			""), //
	UBERMAN("Uberman", "Uberman", NapRole.SUPERHUMAN,//
			"omr2x", //
			"2 hours", //
			"Ultrashort naps", //
			"6 20-min naps", //
			"6 naps, equidistantly spread during the day. 1-hour BRAC and 4-hour rhythm. At least 1 SWS nap, and mixed-stage naps are very likely.", //
			"Extremely hard", //
			"Time slots free for rotation, and equidistant sleep is recommended", //
			"Very high", //
			"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	EXPERIMENTAL("Experimental", "Experimental", NapRole.EXPERIMENTAL,//
			"", //
			"", //
			"", //
			"", //
			"", //
			"", //
			"", //
			"", //
			""), //
	MONO("Mono", "Monophasic", NapRole.MONOPHASIC,//
			"q6fkh", //
			"Around 8 hours", //
			"Unidentified in polyphasic world", //
			"1 long core sleep", //
			"One sleep per day, ideally sleeping in one long chunk through ALL graveyard hours (00:00-08:00)", //
			"Walk in the park", //
			"Core at midnight, wake at 8:00 AM", //
			"Most popular type of sleep", //
			""), //
	UNKNOWN("Unknown", "Unknown", NapRole.MONOPHASIC,//
			"", //
			"", //
			"", //
			"", //
			"", //
			"", //
			"", //
			"", //
			"");

	public final String name;
	public final String longName;
	public final NapRole role;
	public final String napchartID;
	public final String totalSleep;
	public final String identification;
	public final String specification;
	public final String mechanism;
	public final String difficulty;
	public final String scheduling;
	public final String popularity;
	public final String experimental;

	NapSchedule(String name, String longName, NapRole role, String napchartID, String totalSleep, String identification, String specification, String mechanism, String difficulty, String scheduling, String popularity, String experimental)
	{
		this.name = name;
		this.longName = longName;
		this.role = role;
		this.napchartID = napchartID;
		this.totalSleep = totalSleep;
		this.identification = identification;
		this.specification = specification;
		this.mechanism = mechanism;
		this.difficulty = difficulty;
		this.scheduling = scheduling;
		this.popularity = popularity;
		this.experimental = experimental;
	}

	public static String getScheduleList()
	{
		String s = "";
		for (NapSchedule schedule : NapSchedule.values())
		{
			if (schedule == UNKNOWN)
			{
				continue;
			}
			s += (!s.isEmpty() ? " " : "") + "`" + schedule.name + "`";
		}
		return s;
	}
}
