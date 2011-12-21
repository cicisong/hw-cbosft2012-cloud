package healthwatcher.business.factories;

import healthwatcher.view.IFacade;
import lib.exceptions.CommunicationException;

/**
 * Defines methods for facade factories
 */
public abstract class AbstractFacadeFactory {
	
	public abstract IFacade createClientFacade() throws CommunicationException;
	
	public abstract void createServerFacade() throws CommunicationException;
}
