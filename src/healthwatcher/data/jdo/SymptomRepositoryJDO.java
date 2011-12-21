package healthwatcher.data.jdo;

import healthwatcher.data.ISymptomRepository;
import healthwatcher.model.complaint.Symptom;

import java.util.ArrayList;
import java.util.List;

import lib.exceptions.ExceptionMessages;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.PersistenceMechanismException;
import lib.exceptions.RepositoryException;
import lib.exceptions.SQLPersistenceMechanismException;
import lib.persistence.IPersistenceMechanism;
import lib.util.ConcreteIterator;
import lib.util.IteratorDsk;

public class SymptomRepositoryJDO implements ISymptomRepository {

	private IPersistenceMechanism mp;
	
	public SymptomRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
	}

	public void insert(Symptom symptom) throws ObjectNotValidException, ObjectAlreadyInsertedException,
		ObjectNotValidException, RepositoryException {
		
	}
	
	public Symptom search(Long symptomCode) throws ObjectNotFoundException, RepositoryException {
		Symptom symptom = null;		
        return symptom;
	}
	
	public IteratorDsk getSymptomList() throws ObjectNotFoundException, RepositoryException {
        List listaSymptom = new ArrayList();
        return new ConcreteIterator(listaSymptom);

	}
	public void update(Symptom symptom) throws ObjectNotValidException, ObjectNotFoundException,
		ObjectNotValidException, RepositoryException {
		
	}
	
    public boolean exists(Long code) throws RepositoryException {
        boolean response = false;        
        return response;
    }
    
	public void remove(Long code) throws ObjectNotFoundException, RepositoryException {
	}

}