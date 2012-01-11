package healthwatcher.data;

import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.util.IteratorDsk;
import healthwatcher.model.healthguide.HealthUnit;

public interface IHealthUnitRepository {

	public void insert(HealthUnit us) throws ObjectNotValidException,
			ObjectAlreadyInsertedException, ObjectNotValidException, RepositoryException;

	public void update(HealthUnit us) throws ObjectNotValidException, ObjectNotFoundException,
			ObjectNotValidException, RepositoryException;

	public boolean exists(Long num) throws RepositoryException;

	public void remove(Long code) throws ObjectNotFoundException, RepositoryException;

	public HealthUnit search(Long code) throws ObjectNotFoundException, RepositoryException;

	public HealthUnit partialSearch(Long codigo) throws RepositoryException, ObjectNotFoundException; //Thiago incluiu aqui
	
	public IteratorDsk getHealthUnitList() throws ObjectNotFoundException, RepositoryException;

	public IteratorDsk getPartialHealthUnitList() throws ObjectNotFoundException,
			RepositoryException;

	public IteratorDsk getHealthUnitListBySpeciality(Long codEspecialidade)
			throws ObjectNotFoundException, RepositoryException;

}
