package healthwatcher.data.jdo;

import healthwatcher.data.IDiseaseRepository;
import healthwatcher.model.complaint.DiseaseType;
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

public class DiseaseTypeRepositoryJDO implements IDiseaseRepository {

	private IPersistenceMechanism mp;
	
	public DiseaseTypeRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
	}

	public void update(DiseaseType td) throws RepositoryException, ObjectNotFoundException,
			ObjectNotValidException {
	}

	public void filter(Long codigo) throws RepositoryException, ObjectNotFoundException {
	}

	public boolean exists(Long code) throws RepositoryException {
		boolean response = false;
    
        return response;
	}

	public IteratorDsk getDiseaseTypeList() throws RepositoryException, ObjectNotFoundException {
		List listatd = new ArrayList();		
		return new ConcreteIterator(listatd);
	}

	public void insert(DiseaseType td) throws RepositoryException, ObjectAlreadyInsertedException,
			ObjectNotValidException {
		
	}

	/**
	 * M�todo para recuperar um tipo de doen�a do banco de dados.
	 *
	 * @param codigo c�digo do tipo de doen�a a ser procurado
	 * @return um objeto tipo doen�a montado a partir dos dados
	 *           do banco de dados
	 * @throws RepositoryException 
	 */
	public DiseaseType partialSearch(Long codigo) throws ObjectNotFoundException, RepositoryException {
		DiseaseType td = null;
		return td;
	}

	/**
	 * M�todo para recuperar um tipo de doen�a do banco de dados.
	 *
	 * @param code c�digo do tipo de doen�a a ser procurado
	 * @return um objeto tipo doen�a montado a partir dos dados
	 *		   do banco de dados
	 */
	public DiseaseType search(Long code) throws RepositoryException, ObjectNotFoundException {
		DiseaseType td = null;
		return td;
	}
}