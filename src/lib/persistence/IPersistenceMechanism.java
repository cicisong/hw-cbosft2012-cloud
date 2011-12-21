package lib.persistence;

import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;

public interface IPersistenceMechanism {

	public void connect() throws RepositoryException;

	public void disconnect() throws RepositoryException;

	public Object getCommunicationChannel() throws RepositoryException;

	public void releaseCommunicationChannel() throws RepositoryException;
	
	public void beginTransaction() throws TransactionException;

	public void commitTransaction() throws TransactionException;

	public void rollbackTransaction() throws TransactionException;
}
