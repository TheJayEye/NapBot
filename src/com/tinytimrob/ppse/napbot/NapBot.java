package com.tinytimrob.ppse.napbot;

import java.io.File;
import java.sql.Timestamp;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.tinytimrob.common.Application;
import com.tinytimrob.common.CommonUtils;
import com.tinytimrob.common.Configuration;
import com.tinytimrob.common.LogWrapper;
import com.tinytimrob.common.PlatformData;
import com.tinytimrob.common.TerminationReason;
import com.tinytimrob.ppse.napbot.commands.CommandAboutSchedule;
import com.tinytimrob.ppse.napbot.commands.CommandAdaptedList;
import com.tinytimrob.ppse.napbot.commands.CommandChartList;
import com.tinytimrob.ppse.napbot.commands.CommandCreate;
import com.tinytimrob.ppse.napbot.commands.CommandGet;
import com.tinytimrob.ppse.napbot.commands.CommandHelp;
import com.tinytimrob.ppse.napbot.commands.CommandMHelp;
import com.tinytimrob.ppse.napbot.commands.CommandMSet;
import com.tinytimrob.ppse.napbot.commands.CommandMSetNick;
import com.tinytimrob.ppse.napbot.commands.CommandMSetTimestamp;
import com.tinytimrob.ppse.napbot.commands.CommandMemberList;
import com.tinytimrob.ppse.napbot.commands.CommandSay;
import com.tinytimrob.ppse.napbot.commands.CommandScheduleCount;
import com.tinytimrob.ppse.napbot.commands.CommandSet;
import com.tinytimrob.ppse.napbot.commands.CommandXHistoryDump;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

/**
 * NapBot
 * @author Robert Dennington
 */
public class NapBot extends Application
{
	/** Logger */
	static Logger log = LogWrapper.getLogger();

	/** Java Discord API */
	static JDA jda = null;

	/** Whether or not the bot should shut down */
	static volatile TerminationReason terminationReason = null;

	/** Discord Resync ID */
	public static final int RESYNC_ID = 1;

	/** Jetty server */
	static Server SERVER;

	/**
	 * Entry point
	 * @param args Command line arguments
	 */
	public static void main(String[] args)
	{
		Application.execute(new NapBot());
	}

	@Override
	protected String getName()
	{
		return "Nap God";
	}

	@Override
	protected String getVersion()
	{
		return "0.0.4";
	}

	/** The currently loaded configuration data */
	public static NapBotConfiguration CONFIGURATION;

	@Override
	protected TerminationReason run() throws Exception
	{
		//=================================
		// Load configuration
		//=================================
		CONFIGURATION = Configuration.load(NapBotConfiguration.class);
		if (CommonUtils.isNullOrEmpty(CONFIGURATION.authToken))
		{
			log.error("You need to specify your bot's authToken in the configuration file in order for NapBot to work");
			return TerminationReason.STOP;
		}

		//=================================
		// Register commands in the order you want them shown in +help
		//=================================
		NapBotListener.register(new CommandHelp());
		NapBotListener.register(new CommandGet());
		NapBotListener.register(new CommandSet());
		NapBotListener.register(new CommandCreate());
		for (NapSchedule schedule : NapSchedule.values())
		{
			if (!schedule.totalSleep.isEmpty())
			{
				NapBotListener.register(new CommandAboutSchedule(schedule));
			}
		}
		NapBotListener.register(new CommandChartList());
		NapBotListener.register(new CommandMemberList());
		NapBotListener.register(new CommandSay());
		NapBotListener.register(new CommandMHelp());
		NapBotListener.register(new CommandMSet());
		NapBotListener.register(new CommandMSetNick());
		NapBotListener.register(new CommandMSetTimestamp());
		NapBotListener.register(new CommandScheduleCount());
		NapBotListener.register(new CommandAdaptedList());
		NapBotListener.register(new CommandXHistoryDump());

		//=================================
		// Connect to database
		//=================================
		NapBotDb.initialize();

		//=================================
		// Connect to Firefox
		//=================================
		NapchartHandler.init();

		//=================================
		// Start embedded Jetty server for napcharts
		//=================================
		SERVER = new Server();
		ServerConnector httpConnector = new ServerConnector(SERVER);
		httpConnector.setPort(CONFIGURATION.napchartServerPort);
		httpConnector.setName("Main");
		SERVER.addConnector(httpConnector);
		HandlerCollection handlerCollection = new HandlerCollection();
		StatisticsHandler statsHandler = new StatisticsHandler();
		statsHandler.setHandler(handlerCollection);
		SERVER.setStopTimeout(5000);
		SERVER.setHandler(statsHandler);
		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setContextPath("/");
		ServletHolder napchartServlet = new ServletHolder("default", new NapchartServlet());
		contextHandler.addServlet(napchartServlet, "/*");
		handlerCollection.addHandler(contextHandler);
		NCSARequestLog requestLog = new NCSARequestLog(new File(PlatformData.installationDirectory, "logs/requestlog-yyyy_mm_dd.request.log").getAbsolutePath());
		requestLog.setAppend(true);
		requestLog.setExtended(false);
		requestLog.setLogTimeZone("GMT");
		requestLog.setLogLatency(true);
		requestLog.setRetainDays(90);
		requestLog.setLogServer(true);
		requestLog.setPreferProxiedForAddress(true);
		SERVER.setRequestLog(requestLog);
		SERVER.start();
		HttpGenerator.setJettyVersion(this.getName().replace(" ", "") + "/" + this.getVersion().replace(" ", ""));

		//=================================
		// Connect to Discord
		//=================================
		jda = new JDABuilder(AccountType.BOT).setToken(CONFIGURATION.authToken).buildBlocking();
		jda.getPresence().setGame(Game.of("Type " + NapBot.CONFIGURATION.messagePrefix + "help"));
		//		jda.getSelfUser().getManager().setName(this.getName()).complete();
		jda.addEventListener(new NapBotListener());

		//=================================
		// update nap god's schedule
		//=================================
		NapBotDb.setNapchart(jda.getSelfUser(), "https://napchart.com/hu3xo", new Timestamp(1494835930000L));

		//=================================
		// Wait for shutdown
		//=================================
		while (terminationReason == null)
		{
			try
			{
				// don't waste loads of CPU
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				// *shrug*
			}
		}
		return terminationReason;
	}

	@Override
	protected void cleanup() throws Exception
	{
		if (jda != null)
		{
			jda.shutdown();
		}
		if (SERVER != null)
		{
			SERVER.stop();
		}
		NapchartHandler.shutdown();
	}
}
