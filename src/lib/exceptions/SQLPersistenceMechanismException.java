package lib.exceptions;

import lib.logging.*;
import java.util.logging.Level;

public class SQLPersistenceMechanismException extends
		PersistenceMechanismException {
	public String sql;

	public SQLPersistenceMechanismException(String erro, String sql) {
		super(erro);
		this.sql = sql;
		
		LogMechanism.createOccurrence();
		LogMechanism.addLog(Level.WARNING, "SQL Error in statement:"+sql);
	}

}
