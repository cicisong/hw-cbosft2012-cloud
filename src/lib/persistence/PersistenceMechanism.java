package lib.persistence;

import healthwatcher.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import lib.exceptions.ConnectionPersistenceMechanismException;
import lib.exceptions.ExceptionMessages;
import lib.exceptions.PersistenceMechanismException;
import lib.exceptions.RepositoryException;
import lib.exceptions.SQLPersistenceMechanismException;
import lib.exceptions.TransactionException;


public class PersistenceMechanism implements IPersistenceMechanism {

	private static PersistenceMechanism singleton;

	private static int numConexoes = 2;

	private Connection conexoesCriadas[];

	private Connection conexoesLivres[];

	private HashMap conexoesAlocadas;

	private String url;

	private String user;

	private String password;

	private boolean indisponivel;
	
	private int currentMirror = 0;

	private PersistenceMechanism(String url, String user, String password, String driverClass)
			throws RepositoryException {
		conexoesAlocadas = new HashMap();
		this.url = url;
		this.user = user;
		this.password = password;
		indisponivel = false;
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new PersistenceMechanismException("EXC_CLASSE_NAO_ENCONTRADA");
		}
	}

	public synchronized PreparedStatement getPreparedStatement(String sql)
			throws RepositoryException {
		try {
			return getCommunicationChannel(false).prepareStatement(sql);
		} catch (SQLException ex) {
			throw new SQLPersistenceMechanismException("SQLException: " + ex.getMessage(),sql);
		}
	}

	public synchronized void rollbackTransaction() throws TransactionException {
		try {
			Connection con = (Connection) getCommunicationChannel(true);
			con.rollback();
			con.setAutoCommit(true);
			releaseCommunicationChannel(true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException("EXC_CANCELAR_TRANSACAO");
		} finally {
			try {
				notifyAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void connect() throws RepositoryException {

		if (conexoesCriadas == null) {
			try {
				conexoesLivres = new Connection[PersistenceMechanism.numConexoes];
				conexoesCriadas = new Connection[PersistenceMechanism.numConexoes];
				for (int i = 0; i < PersistenceMechanism.numConexoes; i++) {
					if (currentMirror > 0)
						conexoesCriadas[i] = DriverManager.getConnection(url+currentMirror, user, password);
					else
						conexoesCriadas[i] = DriverManager.getConnection(url, user, password);
					conexoesLivres[i] = conexoesCriadas[i];
				}

			} catch (Exception e) {
					incCurrentMirror();
				try{
					this.connect();
				} catch (Exception ex1) {
					ex1.printStackTrace();
					throw new ConnectionPersistenceMechanismException(
					ExceptionMessages.EXC_FALHA_GET_CANAL_COMUNICACAO,this.url);
				}
			}
		}
	}

	public synchronized void commitTransaction() throws TransactionException {
		try {
			Connection con = (Connection) getCommunicationChannel(true);
			con.commit();
			con.setAutoCommit(true);
			releaseCommunicationChannel(true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException("EXC_CONFIRMAR_TRANSACAO");
		} finally {
			notifyAll();
		}
	}

	public synchronized void disconnect() throws RepositoryException {
		try {
			if (conexoesCriadas != null) {
				int fechadas = 0;
				for (int i = 0; i < PersistenceMechanism.numConexoes; i++) {
					System.out.println("Conexao " + i + " " + conexoesLivres[i]);
				}

				System.out.println("Conexoes alocadas " + conexoesAlocadas.size());
				for (int i = 0; i < PersistenceMechanism.numConexoes; i++) {
					if (conexoesCriadas[i] != null) {
						conexoesCriadas[i].close();
						fechadas++;
					}
				}

				conexoesCriadas = null;
				System.out.println("Foram fechadas " + fechadas + " conexoes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceMechanismException(ExceptionMessages.EXC_FALHA_DESCONECTAR);
		}
	}

	/**
	 * Retorna um java.sql.Statement
	 */
	public synchronized Object getCommunicationChannel() throws RepositoryException {
		try {
			return getCommunicationChannel(false).createStatement();
		} catch (Exception ex) {
			incCurrentMirror();
			try {
				this.connect();
				return getCommunicationChannel(false).createStatement();
			} catch (Exception ex1) {
				ex1.printStackTrace();
				throw new ConnectionPersistenceMechanismException(
				ExceptionMessages.EXC_FALHA_GET_CANAL_COMUNICACAO,this.url);
			}
		}
	}

	/**
	 * Retorna um java.sql.Connection
	 */
	private synchronized Connection getCommunicationChannel(boolean porTransacao)
			throws RepositoryException {
		Connection resposta = null;
		try {
			Thread currentThread = Thread.currentThread();
			int threadId = currentThread.hashCode();
			if (conexoesAlocadas.containsKey("" + threadId)) {
				resposta = (Connection) conexoesAlocadas.get("" + threadId);
			} else {
				boolean achou = false;
				do {
					if (achou) {
						break;
					}
					for (int i = 0; !achou && i < PersistenceMechanism.numConexoes; i++) {
						if (conexoesLivres[i] == null) {
							continue;
						}
						achou = true;
						resposta = conexoesLivres[i];
						conexoesLivres[i] = null;
						conexoesAlocadas.put("" + threadId, resposta);
						if (porTransacao) {
							conexoesAlocadas.put("T" + threadId, null);
						}
					}

					if (!achou) {
						indisponivel = true;
						wait();
					}
				} while (true);
			}
		} catch (Exception ex) {
			try{
				this.url = this.url + "1";
				this.connect();
				resposta =  this.getCommunicationChannel(porTransacao);
			} catch (Exception ex1){
				ex.printStackTrace();
				throw new ConnectionPersistenceMechanismException(
					ExceptionMessages.EXC_FALHA_GET_CANAL_COMUNICACAO,this.url);
			}
		}
		return resposta;
	}

	public static synchronized PersistenceMechanism getInstance()
			throws RepositoryException {
		if (singleton == null)
			singleton = new PersistenceMechanism(Constants.DB_URL, Constants.DB_LOGIN,
					Constants.DB_PASS, Constants.DB_DRIVER);
		return singleton;
	}
	
	public static synchronized PersistenceMechanism getInstance(String DB_URL, String DB_LOGIN, String DB_PASS, String DB_DRIVER)
	throws RepositoryException {
		if (singleton == null)
			singleton = new PersistenceMechanism(DB_URL, DB_LOGIN,DB_PASS, DB_DRIVER);
		return singleton;
	}

	public synchronized void beginTransaction() throws TransactionException {
		try {
			while (indisponivel) {
				wait();
			}
			Connection con = (Connection) getCommunicationChannel(true);
			con.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException("EXC_INICIAR_TRANSACAO");
		}
	}

	public synchronized void releaseCommunicationChannel() throws RepositoryException {
		releaseCommunicationChannel(false);
	}

	private synchronized void releaseCommunicationChannel(boolean porTransacao)
			throws RepositoryException {
		try {
			Thread currentThread = Thread.currentThread();
			int threadId = currentThread.hashCode();
			if (porTransacao || !porTransacao && !conexoesAlocadas.containsKey("T" + threadId)) {
				Object canal = conexoesAlocadas.get("" + threadId);
				boolean achou = false;
				for (int i = 0; !achou && i < PersistenceMechanism.numConexoes; i++) {
					if (conexoesLivres[i] != null) {
						continue;
					}
					achou = true;
					conexoesAlocadas.remove("" + threadId);
					if (conexoesAlocadas.containsKey("T" + threadId)) {
						conexoesAlocadas.remove("T" + threadId);
					}
					conexoesLivres[i] = (java.sql.Connection) canal;
				}

				indisponivel = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new PersistenceMechanismException(
					ExceptionMessages.EXC_FALHA_LIBERAR_CANAL_COMUNICACAO);
		} finally {
			notifyAll();
		}
	}


	public void incCurrentMirror() {
		currentMirror = currentMirror + 1;
	}
}
