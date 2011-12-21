package lib.exceptions;

import java.util.logging.Level;

import lib.logging.LogMechanism;

public class ConnectionPersistenceMechanismException extends
		PersistenceMechanismException {
	public String db_url = null;

	public ConnectionPersistenceMechanismException(String erro, String db_url) {
		super(erro);
		this.db_url = db_url;
		LogMechanism.createOccurrence();
		LogMechanism.addLog(Level.SEVERE, "Conection problem with DB:"+db_url);
	}

}
