package healthwatcher.business.factories;

import java.io.IOException;
import java.rmi.RemoteException;

import healthwatcher.Constants;
import healthwatcher.business.HealthWatcherFacade;
import healthwatcher.business.RMIFacadeAdapter;
import healthwatcher.view.IFacade;
import healthwatcher.view.RMIServletAdapter;
import lib.exceptions.CommunicationException;
import lib.exceptions.RepositoryException;

/**
 * A factory for a rmi facade
 */
public class RMIFacadeFactory extends AbstractFacadeFactory {

	public IFacade createClientFacade() throws CommunicationException {
		//return new RMIServletAdapter("//" + Constants.SERVER_NAME + "/" + Constants.SYSTEM_NAME);
		try {
			return HealthWatcherFacade.getInstance();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	public void createServerFacade() throws CommunicationException {
		try {
			RMIFacadeAdapter.getInstance();
		} catch (Exception e) {
			throw new CommunicationException(e.getMessage());
		}
	}
}
