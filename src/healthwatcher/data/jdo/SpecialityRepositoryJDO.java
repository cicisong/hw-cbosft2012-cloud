package healthwatcher.data.jdo;


import healthwatcher.data.ISpecialityRepository;
import healthwatcher.model.healthguide.MedicalSpeciality;

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


public class SpecialityRepositoryJDO implements ISpecialityRepository {

	private IPersistenceMechanism mp;

	public SpecialityRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
	}

	public void update(MedicalSpeciality esp) throws RepositoryException, ObjectNotFoundException,
			ObjectNotValidException {
	}

	public boolean exists(Long code) throws RepositoryException {
		boolean response = false;       
        return response;
	}

	public IteratorDsk getSpecialityList() throws RepositoryException, ObjectNotFoundException {
		List listaEsp = new ArrayList();
		return new ConcreteIterator(listaEsp);
	}

	public void insert(MedicalSpeciality spec) throws RepositoryException,
			ObjectAlreadyInsertedException, ObjectNotValidException {
	}

	public MedicalSpeciality search(Long code) throws RepositoryException, ObjectNotFoundException {
		MedicalSpeciality esp = null;		
		return esp;
	}

	public void remove(Long code) throws RepositoryException, ObjectNotFoundException {
	}
}
