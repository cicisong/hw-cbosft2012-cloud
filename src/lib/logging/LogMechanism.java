package lib.logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogMechanism {

	private static LogMechanism singleton;

	private int IdLog;

	private Logger logger;

	private static String logFile = System.getProperty("java.io.tmpdir") + File.pathSeparator
			+ "hw.log";

	private static synchronized LogMechanism getInstance() {
		if (singleton == null) {
			singleton = new LogMechanism();
			singleton.logger = Logger.getLogger("healthwatcher");
			singleton.IdLog = 0;
			try {
				FileHandler fh = new FileHandler(logFile);
				fh.setFormatter(new SimpleFormatter());
				singleton.logger.addHandler(fh);
			} catch (IOException e) {
			}

		}
		return singleton;
	}

	public static void configure(String file) {
		logFile = file;
	}

	public static synchronized int createOccurrence() {
		getInstance().IdLog = getInstance().IdLog + 1;
		return getInstance().IdLog;

	}

	public static int getLastOccurrence() {
		return getInstance().IdLog;
	}

	public static void addLog(Level level, String message) {
		if (level.getName().equals("SEVERE"))
			getInstance().logger.severe("Log ID:" + getLastOccurrence() + ", Message:" + message);
		else if (level.getName().equals("WARNING"))
			getInstance().logger.warning("Log ID:" + getLastOccurrence() + ", Message:" + message);
		else if (level.getName().equals("FINE"))
			getInstance().logger.fine("Log ID:" + getLastOccurrence() + ", Message:" + message);
		else if (level.getName().equals("INFO"))
			getInstance().logger.info("Log ID:" + getLastOccurrence() + ", Message:" + message);
	}

	public static void addLogToThreads() {
		Thread.setDefaultUncaughtExceptionHandler(new ThreadLogging());
	}

	public static void removeLogToThreads() {
		Thread.setDefaultUncaughtExceptionHandler(null);
	}
}
