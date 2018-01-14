package com.tinytimrob.ppse.napbot;

public enum NapSchedule
{
	BIMAXION("Bimaxion", "Bimaxion", "generalNguyen", NapRole.DUAL_CORE, "Bimax, Quad, Quadphasic", //
			/* NAPCHART:       */"qamnm", //
			/* TOTAL SLEEP:    */"4 hours", //
			/* CLASSIFICATION: */"Dymaxion transitional schedule in a Dual Core style", //
			/* SPECIFICATION:  */"2 core sleeps of minimum length, 2 naps", //
			/* MECHANISM:      */"One core sleep before midnight, one around dawn, Dymaxion noon nap, and Dymaxion afternoon nap. Sleep stage division: Dusk core SWS, dawn core REM. Late nap can contain mixed sleep stages. More flexible than Dymaxion because there are 2 core sleeps to gain SWS and so 2 naps can be moved around to some extent.", //
			/* DIFFICULTY:     */"Hard", //
			/* SCHEDULING:     */"The whole schedule's sleep distribution looks like Dymaxion, with one core before midnight, one core around dawn, one noon nap, one afternoon/evening nap.", //
			/* HEADER:         */"This schedule is intended for people attempting to transition to Dymaxion or retain a Dymaxion sleep rhythm. People looking for a permanent schedule should consider DC3 or E3 instead."), //
	DC1("DC1", "Dual Core 1", "Polyphasic Society", NapRole.DUAL_CORE, "", //
			/* NAPCHART:       */"ugn4g", //
			/* TOTAL SLEEP:    */"5 hours 20 minutes", //
			/* CLASSIFICATION: */"Dual Core schedule", //
			/* SPECIFICATION:  */"2 core sleeps, 1 nap", //
			/* MECHANISM:      */"One core sleep around dusk, one core sleep around dawn, one nap around noon. Sleep stage division: First core with mostly SWS and second core mostly REM.", //
			/* DIFFICULTY:     */"Moderate", //
			/* SCHEDULING:     */"Dusk core around 21:00 to 22:00, dawn core is free to place till 07:00 or 08:00, REM nap around noon or early afternoon", //
			/* HEADER:         */""), //
	DC2("DC2", "Dual Core 2", "Polyphasic Society", NapRole.DUAL_CORE, "", //
			/* NAPCHART:       */"rxpjl", //
			/* TOTAL SLEEP:    */"4 hours 40 minutes", //
			/* CLASSIFICATION: */"Dual Core schedule", //
			/* SPECIFICATION:  */"2 core sleeps, 2 naps", //
			/* MECHANISM:      */"One core sleep around dusk, one core sleep around 02:00ish, one dawn nap and one nap around noon. Sleep stage division: First core SWS, second core REM.", //
			/* DIFFICULTY:     */"Moderate", //
			/* SCHEDULING:     */"First core around 21:00, second core around 02:00, dawn nap before work, noon-early-afternoon nap", //
			/* HEADER:         */""), //
	DC3("DC3", "Dual Core 3", "Polyphasic Society", NapRole.DUAL_CORE, "", //
			/* NAPCHART:       */"arx4r", //
			/* TOTAL SLEEP:    */"4 hours", //
			/* CLASSIFICATION: */"Dual Core schedule", //
			/* SPECIFICATION:  */"2 core sleeps, 3 naps", //
			/* MECHANISM:      */"One core sleep before midnight, one after midnight, dawn nap, noon nap, and afternoon nap. Sleep stage division: First core SWS, second core mixed, naps REM.", //
			/* DIFFICULTY:     */"Hard", //
			/* SCHEDULING:     */"3-hour wake between 2 cores, dawn nap, noon nap and afternoon nap", //
			/* HEADER:         */""), //
	DC4("DC4", "Dual Core 4", "Tinytimrob, based on DC3 and Uberman", NapRole.DUAL_CORE, "", //
			/* NAPCHART:       */"qtny6", //
			/* TOTAL SLEEP:    */"4 hours 20 minutes", //
			/* CLASSIFICATION: */"Uberman transitional schedule in a Dual Core style", //
			/* SPECIFICATION:  */"2 core sleeps, 4 naps", //
			/* MECHANISM:      */"Transitional variant of DC3 with 4 naps that follows Uberman rhythm. One core sleep before midnight, one after midnight, dawn nap, mid-morning nap, mid-afternoon nap, and evening nap. Sleep stage division: First core SWS, second core mixed, naps REM.", //
			/* DIFFICULTY:     */"Hard", //
			/* SCHEDULING:     */"Sleeps are scheduled to start every 4 hours which resembles Uberman. Transition step to E5 or Uberman and also useful as a fallback from E5 or Uberman should adaptation fail. Generally inferior to DC3 unless keeping Uberman rhythm is useful (the extra nap leads to slower sleep onset). Cycle length on this schedule is likely to hit around 65m, so the second core might be shortenable to increase REM quantity in naps (although this is untested)", //
			/* HEADER:         */"This schedule is intended for people attempting to transition to Uberman or retain an Uberman sleep rhythm. People looking for a permanent schedule should consider DC3 or E3 instead."), //
	DYMAXION("Dymaxion", "Dymaxion", "Buckminster Fuller", NapRole.SUPERHUMAN, "Dymax, D4", //
			/* NAPCHART:       */"zgqtz", //
			/* TOTAL SLEEP:    */"2 hours", //
			/* CLASSIFICATION: */"Nap-only schedule", //
			/* SPECIFICATION:  */"4 naps of 30 minutes", //
			/* MECHANISM:      */"4 naps per day, equidistantly spread throughout the day. Circadian spots include: midnight nap, dawn nap, noon nap, and evening nap. Mixed-stage nap(s) in all 4 naps, or possibly pure-REM or pure-SWS nap.", //
			/* DIFFICULTY:     */"Insanely hard", //
			/* SCHEDULING:     */"Arranging naps to fit in 4 aforementioned circadian needs to rest", //
			/* HEADER:         */"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	E1("E1", "Everyman 1", "Puredoxyk", NapRole.BIPHASIC, "Biphasic, E6", //
			/* NAPCHART:       */"30lbn", //
			/* TOTAL SLEEP:    */"6 hours 20 minutes", //
			/* CLASSIFICATION: */"Biphasic schedule in an Everyman style", //
			/* SPECIFICATION:  */"1 long core sleep, 1 short nap", //
			/* MECHANISM:      */"Two sleeps per day, main sleep through graveyard hours and a little rest around noon. Main sleep resembles monophasic sleep the most.", //
			/* DIFFICULTY:     */"Easy", //
			/* SCHEDULING:     */"Core at midnight, nap around noon", //
			/* HEADER:         */"**Not to be confused with Siesta.** The E1 schedule has 1 core + 1 nap, whereas the Siesta schedule has 1 long core + 1 short core"), //
	E2("E2", "Everyman 2", "Puredoxyk", NapRole.EVERYMAN, "E4.5", //
			/* NAPCHART:       */"fflmu", //
			/* TOTAL SLEEP:    */"5 hours 10 minutes", //
			/* CLASSIFICATION: */"Everyman schedule", //
			/* SPECIFICATION:  */"1 long core sleep, 2 REM naps", //
			/* MECHANISM:      */"1 core sleep (3 full cycles), 2 naps", //
			/* DIFFICULTY:     */"Moderate", //
			/* SCHEDULING:     */"Core close to midnight, nap before work, early-afternoon nap", //
			/* HEADER:         */""), //
	E3("E3", "Everyman 3", "Puredoxyk", NapRole.EVERYMAN, "", //
			/* NAPCHART:       */"2b62f", //
			/* TOTAL SLEEP:    */"4 hours", //
			/* CLASSIFICATION: */"Everyman schedule", //
			/* SPECIFICATION:  */"1 core, 3 naps", //
			/* MECHANISM:      */"1 main sleep (2 full cycles), 3 REM naps", //
			/* DIFFICULTY:     */"Hard", //
			/* SCHEDULING:     */"Core as close to dusk as possible to gain more SWS, one nap around 04:00, one nap post-dawn, one early-afternoon nap. All naps should contain REM and little light sleep only.", //
			/* HEADER:         */""), //
	E4("E4", "Everyman 4", "Puredoxyk", NapRole.EVERYMAN, "E1.5", //
			/* NAPCHART:       */"jkfzt", //
			/* TOTAL SLEEP:    */"2 hours 50 minutes", //
			/* CLASSIFICATION: */"Everyman schedule", //
			/* SPECIFICATION:  */"1 min-length core (1 full cycle), 4 REM naps", //
			/* MECHANISM:      */"1 core sleep before midnight to gain as much SWS as possible, 4 naps should only contain REM.", //
			/* DIFFICULTY:     */"Very hard", //
			/* SCHEDULING:     */"Core before midnight, 2 naps before the day starts, noon nap, afternoon nap", //
			/* HEADER:         */"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	E5("E5", "Everyman 5", "Tinytimrob, based on E4 and Uberman", NapRole.EVERYMAN, "", //
			/* NAPCHART:       */"coamr", //
			/* TOTAL SLEEP:    */"3 hours 10 minutes", //
			/* CLASSIFICATION: */"Uberman transitional schedule in an Everyman style", //
			/* SPECIFICATION:  */"1 min-length core (1 full cycle), 5 naps", //
			/* MECHANISM:      */"Transitional variant of E4 with 5 naps that follows Uberman rhythm. 1 core sleep before midnight to gain as much SWS as possible, graveyard nap, dawn nap, mid-morning nap, mid-afternoon nap, and evening nap. A combination of REM-only and mixed-stage naps is likely.", //
			/* DIFFICULTY:     */"Very hard", //
			/* SCHEDULING:     */"Sleeps are scheduled to start every 4 hours which resembles Uberman. Transition step between DC4 and Uberman and also useful as a fallback from Uberman should adaptation fail. Generally inferior to E4 unless keeping Uberman rhythm is useful (the extra nap leads to slower sleep onset)", //
			/* HEADER:         */"This schedule is intended for people attempting to transition to Uberman or retain an Uberman sleep rhythm. People looking for a permanent schedule should consider E4 instead. It is also **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	NAPTATION("Naptation", "Naptation", "Polyphasic Society", NapRole.SUPERHUMAN, "", //
			/* NAPCHART:       */"90uzo", //
			/* TOTAL SLEEP:    */"Up to 4 hours (up to 12 20-min naps per day)", //
			/* CLASSIFICATION: */"Nap-only schedule", //
			/* SPECIFICATION:  */"Multiple short naps", //
			/* MECHANISM:      */"Transitional schedule used by some people to adapt to 20-minute naps before moving to other schedules such as SPAMAYL, Everyman or Uberman.", //
			/* DIFFICULTY:     */"Variable", //
			/* SCHEDULING:     */"Nap once every 1.7 hours. As adaptation progresses, naps can slowly be removed", //
			/* HEADER:         */"This schedule is intended as a transitional schedule only and should not be used as a permanent schedule."), //
	SEGMENTED("Segmented", "Segmented", "N/A, historically used by humans", NapRole.BIPHASIC, "", //
			/* NAPCHART:       */"k0mot", //
			/* TOTAL SLEEP:    */"7 hours", //
			/* CLASSIFICATION: */"Biphasic schedule in a Dual Core style", //
			/* SPECIFICATION:  */"2 core sleeps, with a short gap between them", //
			/* MECHANISM:      */"Two sleeps per day, one sleep in the first half of the night (gives mostly SWS), another sleep in the second half of the night (gives mostly REM), 2-hour wake between 2 cores to trigger sleep stage division between 2 cores", //
			/* DIFFICULTY:     */"Easy", //
			/* SCHEDULING:     */"1 core around 21:00, should not be later than 22:00ish, 1 core till morning", //
			/* HEADER:         */""), //
	SEVAMAYL("SEVAMAYL", "Sleep Everyman As Much As You Like", "aethermind", NapRole.EXPERIMENTAL, "", //
			/* NAPCHART:       */"fqbwo", //
			/* TOTAL SLEEP:    */"Undefined, but on average around 5 hours", //
			/* CLASSIFICATION: */"Flexi schedule in an Everyman style", //
			/* SPECIFICATION:  */"1 core, multiple short naps", //
			/* MECHANISM:      */"Variant of SPAMAYL which includes a core sleep. The core sleep provides SWS and some REM, with naps providing the remaining REM. Strong reliance on the ability to detect when the need to rest is, to get quality REM naps.", //
			/* DIFFICULTY:     */"Variable", //
			/* SCHEDULING:     */"Around a 4 hour core (3 x 80 mins) with up to 6 naps of 10-20 minutes in length. Nap spacing should take into account natural periods of tiredness. Some longer naps might be plausible if all SWS has already been accounted for", //
			/* HEADER:         */"This schedule is **experimental** and **not well tested**, with the specifics still being under debate and scrutiny.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule and instead stick to something better tested (such as E2 or E3)."), //
	SIESTA("Siesta", "Siesta", "N/A, historically used by humans", NapRole.BIPHASIC, "", //
			/* NAPCHART:       */"e72xy", //
			/* TOTAL SLEEP:    */"6 hours 30 minutes", //
			/* CLASSIFICATION: */"Biphasic schedule", //
			/* SPECIFICATION:  */"1 long core + 1 short core, placed roughly opposite each other", //
			/* MECHANISM:      */"Two sleeps per day. Main sleep through most of the night with a long rest of 1 full cycle around noon", //
			/* DIFFICULTY:     */"Easy", //
			/* SCHEDULING:     */"Core around midnight, siesta at noon", //
			/* HEADER:         */"**Not to be confused with E1.** The E1 schedule has 1 core + 1 nap, whereas the Siesta schedule has 1 long core + 1 short core"), //
	SPAMAYL("SPAMAYL", "Sleep Polyphasically As Much As You Like", "Rasmus", NapRole.SUPERHUMAN, "", //
			/* NAPCHART:       */"yh1pp", //
			/* TOTAL SLEEP:    */"Undefined, but on average 2.5 hours (7-8 20-min naps per day)", //
			/* CLASSIFICATION: */"Flexi schedule in a nap-only style", //
			/* SPECIFICATION:  */"Multiple short naps", //
			/* MECHANISM:      */"No rhythm, no BRAC concepts, there usually have to have more than 6 20-minute naps per day average. Stocking up sleep in a day then having few or even no naps the next day is also possible. Strong reliance on the ability to detect when the need to rest is, to get quality REM naps. At least 1 SWS nap, and some other naps can be mixed-stages nap. SPAMAYL can be stabilized and formed into a rhythm if one has a stable work/schedule everyday (set nap times, like 2-hour rhythm, napping once every 1.7 hours at night for example).", //
			/* DIFFICULTY:     */"Insanely hard", //
			/* SCHEDULING:     */"Most naps should be spread during graveyard hours to avoid the need to nap during the day with work, social life, etc", //
			/* HEADER:         */"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	TESLA("Tesla", "Tesla", "Sharif", NapRole.SUPERHUMAN, "U4", //
			/* NAPCHART:       */"s2x5u", //
			/* TOTAL SLEEP:    */"1 hour 20 minutes", //
			/* CLASSIFICATION: */"Nap-only schedule", //
			/* SPECIFICATION:  */"4 naps of 20 minutes", //
			/* MECHANISM:      */"Variant of Dymaxion with 20 minute naps instead of 30 minute naps. 4 naps per day, equidistantly spread throughout the day. Circadian spots include: midnight nap, dawn nap, noon nap, and evening nap. Mixed-stage nap(s) in all 4 naps, or possibly pure-REM or pure-SWS nap.", //
			/* DIFFICULTY:     */"Insanely hard", //
			/* SCHEDULING:     */"Arranging naps to fit in 4 aforementioned circadian needs to rest", //
			/* HEADER:         */"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	TC1("TC1", "Tri Core 1", "generalNguyen", NapRole.TRI_CORE, "", //
			/* NAPCHART:       */"tocl4", //
			/* TOTAL SLEEP:    */"4 hours 50 minutes", //
			/* CLASSIFICATION: */"Tri Core schedule", //
			/* SPECIFICATION:  */"3 core sleeps of minimum length, 1 nap", //
			/* MECHANISM:      */"Sleep stage division: 1 SWS core, 2 REM core and 1 REM nap. 3-hour rhythm to line up with sleep-wake cycle length of each core sleep. Predicted to be more flexible than Dual Core sleep schedules because the core component of Tri Core consists of 3 cores to be shifted quite comfortably once adapted. ", //
			/* DIFFICULTY:     */"Moderate", //
			/* SCHEDULING:     */"3 core sleeps are concentrated during the night to boost alertness for the whole day, so only one small nap in the afternoon is needed. 3-hour rhythm among each core sleep is recommended. Distance of wake time among each core sleep could be shortened if possible, but should not be less than 2 hours. Such a schedule will include a dusk core, night core, and dawn core.", //
			/* HEADER:         */""), //
	TC2("TC2", "Tri Core 2", "LichTerLoh", NapRole.TRI_CORE, "", //
			/* NAPCHART:       */"43swa", //
			/* TOTAL SLEEP:    */"5 hours 10 minutes", //
			/* CLASSIFICATION: */"Tri Core schedule", //
			/* SPECIFICATION:  */"3 core sleeps of minimum length, 2 naps", //
			/* MECHANISM:      */"Sleep stage division: 1 SWS core, 2 REM core and 2 REM naps", //
			/* DIFFICULTY:     */"Moderate", //
			/* SCHEDULING:     */"Evening core, graveyard core, dawn core and 2 daytime naps", //
			/* HEADER:         */""), //
	TRIMAXION("Trimaxion", "Trimaxion", "generalNguyen", NapRole.EVERYMAN, "", //
			/* NAPCHART:       */"awkwb", //
			/* TOTAL SLEEP:    */"3 hours", //
			/* CLASSIFICATION: */"Dymaxion transitional schedule in an Everyman style", //
			/* SPECIFICATION:  */"1 core sleep of minimum length (one full cycle), 3 Dymaxion naps", //
			/* MECHANISM:      */"4 sleeps per day. Core sleep before midnight for most SWS, naps for REM/mixed stages. Circadian spots include: Core sleep around dusk, dawn nap, noon nap, and evening nap. Mixed-stage nap(s) in all 3 naps, or possibly pure-REM or pure-SWS nap. More flexible than Dymaxion because of having a core sleep, so after adaptation 3 naps can be moved around when convenient.", //
			/* DIFFICULTY:     */"Very hard", //
			/* SCHEDULING:     */"Distribution of sleep resembles Dymaxion. Transition step to Dymaxion and also as a fallback from Dymaxion should adaptation fail.", //
			/* HEADER:         */"This schedule is intended for people attempting to transition to Dymaxion or retain a Dymaxion sleep rhythm. People looking for a permanent schedule should consider E4 instead. It is also **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	TRIPHASIC("Triphasic", "Triphasic", "Leif Weaver", NapRole.TRI_CORE, "Tri Core 0, TC0", //
			/* NAPCHART:       */"8z46u", //
			/* TOTAL SLEEP:    */"4 hours 30 minutes", //
			/* CLASSIFICATION: */"Tri Core schedule", //
			/* SPECIFICATION:  */"3 cores of minimum length", //
			/* MECHANISM:      */"One core sleep before midnight, one around dawn, and one around noon. Sleep stage separation of SWS and REM into 2 cores of the night, like Dual Core sleep. Long siesta to balance out the schedule.", //
			/* DIFFICULTY:     */"Moderate", //
			/* SCHEDULING:     */"One core sleep around 21:00, one around 05:30, one around noon", //
			/* HEADER:         */""), //
	UBERMAN("Uberman", "Uberman", "Puredoxyk", NapRole.SUPERHUMAN, "Uber, U6", //
			/* NAPCHART:       */"omr2x", //
			/* TOTAL SLEEP:    */"2 hours", //
			/* CLASSIFICATION: */"Nap-only schedule", //
			/* SPECIFICATION:  */"6 20-min naps", //
			/* MECHANISM:      */"6 naps, equidistantly spread during the day. At least 1 SWS nap, and mixed-stage naps are very likely.", //
			/* DIFFICULTY:     */"Insanely hard", //
			/* SCHEDULING:     */"Time slots free for rotation, and equidistant sleep is recommended", //
			/* HEADER:         */"This schedule is **below the minimum sleep threshold of most people** and consequently has a **very low success rate**.  It is **strongly recommended** that inexperienced polyphasers do **NOT** attempt this schedule."), //
	EXPERIMENTAL("Experimental", "Experimental", "", NapRole.EXPERIMENTAL, "", //
			/* NAPCHART:       */"", //
			/* TOTAL SLEEP:    */"", //
			/* CLASSIFICATION: */"", //
			/* SPECIFICATION:  */"", //
			/* MECHANISM:      */"", //
			/* DIFFICULTY:     */"", //
			/* SCHEDULING:     */"", //
			/* HEADER:         */""), //
	MONO("Mono", "Monophasic", "N/A, historically used by humans", NapRole.MONOPHASIC, "Hibernation", //
			/* NAPCHART:       */"q6fkh", //
			/* TOTAL SLEEP:    */"Around 8 hours", //
			/* CLASSIFICATION: */"Monophasic", //
			/* SPECIFICATION:  */"1 long core sleep", //
			/* MECHANISM:      */"One sleep per day, ideally sleeping in one long chunk through ALL graveyard hours (00:00-08:00)", //
			/* DIFFICULTY:     */"Walk in the park", //
			/* SCHEDULING:     */"Core at midnight, wake at 8:00 AM", //
			/* HEADER:         */""), //
	UNKNOWN("Unknown", "Unknown", "", NapRole.MONOPHASIC, "", //
			/* NAPCHART:       */"", //
			/* TOTAL SLEEP:    */"", //
			/* CLASSIFICATION: */"", //
			/* SPECIFICATION:  */"", //
			/* MECHANISM:      */"", //
			/* DIFFICULTY:     */"", //
			/* SCHEDULING:     */"", //
			/* HEADER:         */"");

	public final String name;
	public final String longName;
	public final String inventor;
	public final NapRole role;
	public final String alternativeNames;
	public final String napchartID;
	public final String totalSleep;
	public final String classification;
	public final String specification;
	public final String mechanism;
	public final String difficulty;
	public final String scheduling;
	public final String experimental;

	NapSchedule(String name, String longName, String inventor, NapRole role, String alternativeNames, String napchartID, String totalSleep, String classification, String specification, String mechanism, String difficulty, String scheduling, String experimental)
	{
		this.name = name;
		this.longName = longName;
		this.inventor = inventor;
		this.role = role;
		this.alternativeNames = alternativeNames;
		this.napchartID = napchartID;
		this.totalSleep = totalSleep;
		this.classification = classification;
		this.specification = specification;
		this.mechanism = mechanism;
		this.difficulty = difficulty;
		this.scheduling = scheduling;
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
