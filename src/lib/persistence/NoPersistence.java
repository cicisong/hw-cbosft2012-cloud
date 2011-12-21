package lib.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;

import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
public class NoPersistence implements IPersistenceMechanism {

	private static NoPersistence singleton;

	
	private NoPersistence()
			throws RepositoryException {
	}

	public synchronized PreparedStatement getPreparedStatement(String sql)
			throws RepositoryException {
		return null;
	}

	public synchronized void rollbackTransaction() throws TransactionException {
	}

	public synchronized void connect() throws RepositoryException {

	}

	public synchronized void commitTransaction() throws TransactionException {
	}

	public synchronized void disconnect() throws RepositoryException {
	}

	/**
	 * Retorna um java.sql.Statement
	 */
	public synchronized Object getCommunicationChannel() throws RepositoryException {
		return null;
	}

	/**
	 * Retorna um java.sql.Connection
	 */
	private synchronized Connection getCommunicationChannel(boolean porTransacao)
			throws RepositoryException {
		return null;
	}

	public static synchronized NoPersistence getInstance()
			throws RepositoryException {
		if (singleton == null)
			singleton = new NoPersistence();
		return singleton;
	}
	
	public static synchronized NoPersistence getInstance(String DB_URL, String DB_LOGIN, String DB_PASS, String DB_DRIVER)
	throws RepositoryException {
		if (singleton == null)
			singleton = new NoPersistence();
		return singleton;
	}

	public synchronized void beginTransaction() throws TransactionException {
	}

	public synchronized void releaseCommunicationChannel() throws RepositoryException {

	}

	private synchronized void releaseCommunicationChannel(boolean porTransacao)
			throws RepositoryException {
	}


	public void incCurrentMirror() {

	}
}
