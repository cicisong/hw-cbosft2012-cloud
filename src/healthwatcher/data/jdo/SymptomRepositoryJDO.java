package healthwatcher.data.jdo;

import healthwatcher.data.ISymptomRepository;
import healthwatcher.model.complaint.Symptom;
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
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Transaction tx = pm.currentTransaction();

		try{
			tx.begin();
			pm.makePersistent(symptom);
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
	
	public Symptom search(Long symptomCode) throws ObjectNotFoundException, RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Symptom esp = null;
		try{
			String query = ("select from "+Symptom.class.getName()+" where id == "+symptomCode+"");
			List<Symptom> spec = (List<Symptom>)pm.newQuery(query).execute();
			esp = spec.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return esp;
	}
	
	public IteratorDsk getSymptomList() throws ObjectNotFoundException, RepositoryException {
		List<Symptom> listaEsp = new ArrayList<Symptom>();
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		// Query para selecionar os códigos de todas unidades de saúde
		// existentes no sistema
		Query q = pm.newQuery("SELECT id FROM "+Symptom.class.getName());

		try {
			List<Long> ids = (List<Long>) q.execute();

			if (!ids.isEmpty()) {
				Symptom ms = search(ids.get(0));
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
				Symptom ms = search(ids.get(0));
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
	public void update(Symptom symptom) throws ObjectNotValidException, ObjectNotFoundException,
		ObjectNotValidException, RepositoryException {
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		try {
			Symptom e = pm.getObjectById(Symptom.class, symptom.getId());
			e.setDescription(symptom.getDescription());
		} finally {
			pm.close();
		}
	}
	
    public boolean exists(Long code) throws RepositoryException {
    	boolean response = false;
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		long asdf = code;
		int hehe = (int)asdf;
		Query q = pm.newQuery("SELECT FROM "+Symptom.class.getName()+" where teste == "+hehe+"");
		try {
			List<Symptom> ids = (List<Symptom>) q.execute();

			if (ids.size() > 0){
				response = true;
			}
			pm.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
    }
    
	public void remove(Long code) throws ObjectNotFoundException, RepositoryException {
	}

}