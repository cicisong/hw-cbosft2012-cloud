package healthwatcher.business;

import healthwatcher.Constants;
import healthwatcher.business.factories.FacadeFactory;
import lib.logging.*;

/**
 * TODO - describe this file
 * 
 */
public class HWServer {

	public static void main(String[] args) {
		try {
			LogMechanism.configure(Constants.LOG_PATH);
			FacadeFactory.getRepositoryFactory().createServerFacade();
			LogMechanism.addLogToThreads();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
