package healthwatcher.data.jdo;


import healthwatcher.data.ISpecialityRepository;
import healthwatcher.model.healthguide.MedicalSpeciality;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import lib.exceptions.ExceptionMessages;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.PersistenceMechanismException;
import lib.exceptions.RepositoryException;
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
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		try {
			MedicalSpeciality e = pm.getObjectById(MedicalSpeciality.class, esp.getId());
			e.setDescricao(esp.getDescricao());
		} finally {
			pm.close();
		}
	}

	public boolean exists(Long code) throws RepositoryException {
		boolean response = false;       
        return response;
	}

	public IteratorDsk getSpecialityList() throws RepositoryException, ObjectNotFoundException {		
		List<MedicalSpeciality> listaEsp = new ArrayList<MedicalSpeciality>();
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		// Query para selecionar os códigos de todas unidades de saúde
		// existentes no sistema
		Query q = pm.newQuery("SELECT id FROM "+MedicalSpeciality.class.getName());

		try {
			List<Long> ids = (List<Long>) q.execute();

			if (!ids.isEmpty()) {
				MedicalSpeciality ms = search(ids.get(0));
				listaEsp.add(ms);
				try {
					ids.remove(0);
				} catch (UnsupportedOperationException uoe) {
					ids = new ArrayList<Long>(ids);
					ids.remove(0);
				}
			} else {
				throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}

			while (!ids.isEmpty()) {
				MedicalSpeciality ms = search(ids.get(0));
				listaEsp.add(ms);
				ids.remove(0);
			}
			pm.close();
		} catch (PersistenceMechanismException e) {
			throw new RepositoryException(ExceptionMessages.EXC_FALHA_PROCURA);
		} finally {
			try {
				mp.releaseCommunicationChannel();
			} catch (PersistenceMechanismException e) {
				throw new RepositoryException(e.getMessage());
			}
		}
		return new ConcreteIterator(listaEsp);
	}

	public void insert(MedicalSpeciality spec) throws RepositoryException,
			ObjectAlreadyInsertedException, ObjectNotValidException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Transaction tx = pm.currentTransaction();

		try{
			tx.begin();
			pm.makePersistent(spec);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (tx.isActive()){
				tx.rollback();
			}
			pm.close();
		}
	}

	public MedicalSpeciality search(Long code) throws RepositoryException, ObjectNotFoundException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		MedicalSpeciality esp = null;
		
		try{
			String query = ("select from "+MedicalSpeciality.class.getName()+" where id == "+code+"");
			List<MedicalSpeciality> spec = (List<MedicalSpeciality>)pm.newQuery(query).execute();
			
			esp = spec.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return esp;
		
	}

	public void remove(Long code) throws RepositoryException, ObjectNotFoundException {
	}
}
