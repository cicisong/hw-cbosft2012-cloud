package lib.logging;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Level;

public class ThreadLogging implements UncaughtExceptionHandler {

	public void uncaughtException(Thread t, Throwable e) {
		LogMechanism.createOccurrence();
		LogMechanism.addLog(Level.WARNING, "Problem with Thread:" + t.getName() + ", Exception:"
				+ e.toString() + ", Line:" + e.getStackTrace()[0].getLineNumber() + ", Method:"
				+ e.getStackTrace()[0].getMethodName());
	}
}
