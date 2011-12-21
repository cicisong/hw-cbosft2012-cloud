package healthwatcher.data.jdo;

import healthwatcher.data.IHealthUnitRepository;
import healthwatcher.model.healthguide.HealthUnit;
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


public class HealthUnitRepositoryJDO implements IHealthUnitRepository {

	private IPersistenceMechanism mp;

	private SpecialityRepositoryJDO specialityRep;

	public HealthUnitRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
		specialityRep = new SpecialityRepositoryJDO(mp);
	}

	public void update(HealthUnit us) throws RepositoryException, ObjectNotFoundException,
			ObjectNotValidException {

	}

	public boolean exists(Long code) throws RepositoryException {
		boolean response = false;       
        return response;
	}

	public IteratorDsk getHealthUnitList() throws RepositoryException, ObjectNotFoundException {
		List listaUs = new ArrayList();
		return new ConcreteIterator(listaUs);
	}

	public IteratorDsk getPartialHealthUnitList() throws RepositoryException,
			ObjectNotFoundException {
		List listaUs = new ArrayList();
		return new ConcreteIterator(listaUs);
	}

	public IteratorDsk getHealthUnitListBySpeciality(Long code) throws RepositoryException,
			ObjectNotFoundException {
		List listaUS = new ArrayList();
		return new ConcreteIterator(listaUS);
	}

	public void insert(HealthUnit hu) throws RepositoryException, ObjectAlreadyInsertedException,
			ObjectNotValidException {

	}

	public HealthUnit search(Long code) throws RepositoryException, ObjectNotFoundException {
		HealthUnit us = null;		
		return us;
	}

	public void remove(Long codigo) throws RepositoryException, ObjectNotFoundException {
	}

	public HealthUnit partialSearch(int codigo) throws RepositoryException, ObjectNotFoundException {
		HealthUnit hu = null;		
		return hu;
	}
}
