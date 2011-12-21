package lib.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
public class PersistenceMechanismJDO implements IPersistenceMechanism {

	private static PersistenceMechanismJDO singleton;
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	
	private PersistenceMechanismJDO() throws RepositoryException {
		get();
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
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
		return pmfInstance.getPersistenceManager();
	}

	/**
	 * Retorna um java.sql.Connection
	 */
	private synchronized Connection getCommunicationChannel(boolean porTransacao) throws RepositoryException {
		return null;
	}

	public static synchronized PersistenceMechanismJDO getInstance()
			throws RepositoryException {
		if (singleton == null)
			singleton = new PersistenceMechanismJDO();
		return singleton;
	}
	
	public static synchronized PersistenceMechanismJDO getInstance(String DB_URL, String DB_LOGIN, String DB_PASS, String DB_DRIVER)
	throws RepositoryException {
		if (singleton == null)
			singleton = new PersistenceMechanismJDO();
		return singleton;
	}

	public synchronized void beginTransaction() throws TransactionException {
	}

	public synchronized void releaseCommunicationChannel() throws RepositoryException {

	}

	private synchronized void releaseCommunicationChannel(boolean porTransacao) throws RepositoryException {
	}

	public void incCurrentMirror() {

	}
}
