package healthwatcher.business.complaint;

import healthwatcher.data.IDiseaseRepository;
import healthwatcher.model.complaint.DiseaseType;
import lib.concurrency.ConcurrencyManager;
import lib.exceptions.ExceptionMessages;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.util.IteratorDsk;



public class DiseaseRecord {

	private IDiseaseRepository diseaseRep;
	private ConcurrencyManager manager = new ConcurrencyManager();
	
	public DiseaseRecord(IDiseaseRepository repTipoDoenca) {
		this.diseaseRep = repTipoDoenca;
	}

	public DiseaseType search(Long codigo) throws RepositoryException, ObjectNotFoundException {
		return diseaseRep.search(codigo);
	}

	public IteratorDsk getDiseaseTypeList() throws RepositoryException, ObjectNotFoundException {
		return diseaseRep.getDiseaseTypeList();
	}
	
	public void insert(DiseaseType diseaseType) throws RepositoryException, ObjectAlreadyInsertedException,
		ObjectNotValidException {
		try { 
			//#if relacional
			manager.beginExecution("" + diseaseType.getId());
			
			if (diseaseRep.exists(diseaseType.getId())) {
				throw new ObjectAlreadyInsertedException(ExceptionMessages.EXC_JA_EXISTE);
			} else {
				this.diseaseRep.insert(diseaseType);
			}
			//#endif
			
			//#if norelacional
//@			if (diseaseRep.exists((long) diseaseType.getCode())) {
//@				throw new ObjectAlreadyInsertedException(ExceptionMessages.EXC_JA_EXISTE);
//@			} else {
//@				this.diseaseRep.insert(diseaseType);
//@			}
			//#endif

		}finally {			
			//#if relacional
			manager.endExecution("" + diseaseType.getId());
			//#endif
		}		
	}
}
