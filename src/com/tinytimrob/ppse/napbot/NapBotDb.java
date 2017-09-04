package com.tinytimrob.ppse.napbot;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import com.tinytimrob.common.LogWrapper;
import com.tinytimrob.common.PlatformData;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class NapBotDb
{
	static Logger log = LogWrapper.getLogger();
	private static Connection CONNECTION = null;
	private static final long EXPECTED_SCHEMA_VERSION = 1;

	static void initialize() throws SQLException, IOException
	{
		CONNECTION = DriverManager.getConnection("jdbc:sqlite:napbot.db");

		int schemaVersion;
		if (!tableExists("meta"))
		{
			schemaVersion = 0;
		}
		else
		{
			Statement vq = create();
			try
			{
				ResultSet vqResult = vq.executeQuery("SELECT version FROM meta");
				vqResult.next();
				schemaVersion = vqResult.getInt("version");
			}
			finally
			{
				vq.close();
			}
		}
		if (schemaVersion != EXPECTED_SCHEMA_VERSION)
		{
			// Create a database backup
			File databaseMasterFile = new File(PlatformData.installationDirectory, "napbot.db");
			File databaseBackupFile = new File(PlatformData.installationDirectory, "database_backup_" + System.currentTimeMillis());
			log.info("The database schema is currently version " + schemaVersion + " but the expected version is " + EXPECTED_SCHEMA_VERSION + ". A database update will now be performed.");
			log.info("In the event that the update fails, you can restore a database backup from " + databaseBackupFile.getAbsolutePath());
			FileUtils.copyFile(databaseMasterFile, databaseBackupFile);

			// Now update the database to the latest schema version one step at a time
			// If you add a new schema version, add a NONBREAKING case statement!
			// This will let the changing propagate through each version
			switch (schemaVersion)
			{
			case 0:
				// Version 0 means a version before we tracked the version
				log.info("Updating database schema to version 1");
				if (!tableExists("meta"))
				{
					executeUpdate("CREATE TABLE meta (version INTEGER DEFAULT 0)");
					executeUpdate("INSERT INTO meta (version) VALUES (0)");
				}
				if (tableExists("napcharts"))
				{
					executeUpdate("ALTER TABLE napcharts ADD COLUMN time TIMESTAMP");
				}
				else
				{
					executeUpdate("CREATE TABLE IF NOT EXISTS napcharts (id TEXT PRIMARY KEY NOT NULL, link TEXT, time TIMESTAMP)");
				}
				setSchemaVersion(1);
				// nobreak
			}
		}
		log.info("Database initialized successfully");
	}

	public static Statement create() throws SQLException
	{
		return CONNECTION.createStatement();
	}

	public static PreparedStatement prepare(String sql) throws SQLException
	{
		return CONNECTION.prepareStatement(sql);
	}

	public static int executeUpdate(String sql) throws SQLException
	{
		Statement statement = create();
		try
		{
			return statement.executeUpdate(sql);
		}
		finally
		{
			statement.close();
		}
	}

	public static boolean tableExists(String name) throws SQLException
	{
		PreparedStatement statement = prepare("SELECT name FROM sqlite_master WHERE type = 'table' AND name=?");
		try
		{
			statement.setString(1, name);
			return statement.executeQuery().next();
		}
		finally
		{
			statement.close();
		}
	}

	private static void setSchemaVersion(long version) throws SQLException
	{
		PreparedStatement statement = prepare("UPDATE meta SET version = ?");
		try
		{
			statement.setLong(1, version);
			statement.executeUpdate();
		}
		finally
		{
			statement.close();
		}
	}

	public static void setNapchart(User user, String napchart) throws SQLException
	{
		setNapchart(user, napchart, new Timestamp(System.currentTimeMillis()));
	}

	public static void setNapchart(User user, String napchart, Timestamp timestamp) throws SQLException
	{
		napchart = napchart.replace("http://", "https://");
		PreparedStatement ps = prepare("INSERT OR REPLACE INTO napcharts (id, link, time) VALUES (?, ?, ?)");
		ps.setLong(1, user.getIdLong());
		ps.setString(2, napchart);
		ps.setTimestamp(3, timestamp);
		ps.executeUpdate();
		try
		{
			NapchartHandler.getNapchart(napchart.substring(napchart.length() - 5, napchart.length()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void setNapchartTimestamp(User user, Timestamp timestamp) throws SQLException
	{
		PreparedStatement ps = prepare("UPDATE napcharts SET time = ? WHERE id = ?");
		ps.setTimestamp(1, timestamp);
		ps.setLong(2, user.getIdLong());
		ps.executeUpdate();
	}

	public static String removeNapchart(User user, TextChannel channel) throws SQLException
	{
		PreparedStatement ps = prepare("SELECT * FROM napcharts WHERE id = ? LIMIT 1");
		ps.setLong(1, user.getIdLong());
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			String napchartLocation = rs.getString("link").replace("http://", "https://");
			ps = prepare("DELETE FROM napcharts WHERE id = ?");
			ps.setLong(1, user.getIdLong());
			ps.executeUpdate();
			return "\n**Attention:** Your old napchart (<" + napchartLocation + ">) was removed. If you wanted to keep it, type `" + NapBot.CONFIGURATION.messagePrefix + "set " + napchartLocation + "` to set it against your name again.";
		}
		return "";
	}
}
