package healthwatcher.business.complaint;

import healthwatcher.data.ISymptomRepository;
import healthwatcher.model.complaint.Symptom;
import lib.concurrency.ConcurrencyManager;
import lib.exceptions.ExceptionMessages;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.util.IteratorDsk;


public class SymptomRecord {

	private ISymptomRepository rep;
	private ConcurrencyManager manager = new ConcurrencyManager();
	
	public SymptomRecord(ISymptomRepository rep) {
		this.rep = rep;
	}

	public void insert(Symptom symptom) throws RepositoryException, ObjectAlreadyInsertedException,
			ObjectNotValidException {
		try {
			//#if relacional
			manager.beginExecution("" + symptom.getId());
			if (rep.exists(symptom.getId())) {
				throw new ObjectAlreadyInsertedException(ExceptionMessages.EXC_JA_EXISTE);
			} else {
				rep.insert(symptom);
			}
			//#endif
			
			//#if norelacional
//@			if (rep.exists((long) symptom.getCode())) {
//@				throw new ObjectAlreadyInsertedException(ExceptionMessages.EXC_JA_EXISTE);
//@			} else {
//@				this.rep.insert(symptom);
//@			}
			//#endif
			
		} finally {
			//#if relacional
			manager.endExecution("" + symptom.getId());
			//#endif
		}
	}

	public Symptom search(Long code) throws ObjectNotFoundException,	RepositoryException {
		return rep.search(code);
	}

	public IteratorDsk getSymptomList() throws RepositoryException, ObjectNotFoundException {
		return rep.getSymptomList();
	}

	public void update(Symptom symptom) throws RepositoryException, ObjectNotFoundException,
			ObjectNotValidException {
		rep.update(symptom);
	}
}
