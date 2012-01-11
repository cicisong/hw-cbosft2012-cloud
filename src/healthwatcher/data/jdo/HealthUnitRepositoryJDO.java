package healthwatcher.data.jdo;

import healthwatcher.data.IHealthUnitRepository;
import healthwatcher.model.healthguide.HealthUnit;
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


public class HealthUnitRepositoryJDO implements IHealthUnitRepository {

	private IPersistenceMechanism mp;

	private SpecialityRepositoryJDO specialityRep;

	public HealthUnitRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
		specialityRep = new SpecialityRepositoryJDO(mp);
	}

	public void update(HealthUnit us) throws RepositoryException, ObjectNotFoundException,
	ObjectNotValidException {
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		try {
			HealthUnit e = pm.getObjectById(HealthUnit.class, us.getId());
			e.setDescription(us.getDescription());
		} finally {
			pm.close();
		}
	}

	public boolean exists(Long code) throws RepositoryException {
		boolean response = false;       
		return response;
	}

	public IteratorDsk getHealthUnitList() throws RepositoryException, ObjectNotFoundException {
		List<HealthUnit> listaUs = new ArrayList<HealthUnit>();
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();

		// Query para selecionar os códigos de todas unidades de saúde
		// existentes no sistema
		Query q = pm.newQuery("SELECT id FROM "+HealthUnit.class.getName());

		try {
			List<Long> ids = (List<Long>) q.execute();
			// O resultado da query é testado para saber
			// da existência de unidades de saúde cadastradas.
			// Caso não existam uma exceção é lançada.
			if (!ids.isEmpty()) {
				HealthUnit us = search(ids.get(0));
				listaUs.add(us);
				try {
					ids.remove(0);
				} catch (UnsupportedOperationException uoe) {
					ids = new ArrayList<Long>(ids);
					ids.remove(0);
				}
			} else {
				throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}

			// O resultado da query é navegado, e cada
			// código é informado à um método (procura) que
			// monta uma unidade de sáude a partir do código.
			while (!ids.isEmpty()) {
				HealthUnit us = new HealthUnit();
				us = search(ids.get(0));
				listaUs.add(us);
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
		// O retorno desse método é uma estrutura que permite a
		// iteração nos elementos
		return new ConcreteIterator(listaUs);
	}

	public IteratorDsk getPartialHealthUnitList() throws RepositoryException,
	ObjectNotFoundException {
		List<HealthUnit> listaUs = new ArrayList<HealthUnit>();
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();

		// Query para selecionar os códigos de todas unidades de saúde
		// existentes no sistema
		Query q = pm.newQuery("SELECT id FROM "+HealthUnit.class.getName());

		try {
			List<Long> ids = (List<Long>) q.execute();
			// O resultado da query é testado para saber
			// da existência de unidades de saúde cadastradas.
			// Caso não existam uma exceção é lançada.
			if (!ids.isEmpty()) {
				HealthUnit us = search(ids.get(0));
				listaUs.add(us);
				try {
					ids.remove(0);
				} catch (UnsupportedOperationException uoe) {
					ids = new ArrayList<Long>(ids);
					ids.remove(0);
				}
			} else {
				throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}

			// O resultado da query é navegado, e cada
			// código é informado à um método (procura) que
			// monta uma unidade de sáude a partir do código.
			while (!ids.isEmpty()) {
				HealthUnit us = new HealthUnit();
				us = search(ids.get(0));
				listaUs.add(us);
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
		// O retorno desse método é uma estrutura que permite a
		// iteração nos elementos
		return new ConcreteIterator(listaUs);
	}

	public IteratorDsk getHealthUnitListBySpeciality(Long code) throws RepositoryException,
	ObjectNotFoundException {
		List<HealthUnit> listaUs = new ArrayList<HealthUnit>();
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		long asdf = code;
		int hehe = (int)asdf;
		// Query para selecionar os códigos de todas unidades de saúde
		// existentes no sistema
		Query q = pm.newQuery("SELECT FROM "+HealthUnit.class.getName()+" where teste == "+hehe+"");
		try {
			List<HealthUnit> ids = (List<HealthUnit>) q.execute();
			
			// O resultado da query é testado para saber
			// da existência de unidades de saúde cadastradas.
			// Caso não existam uma exceção é lançada.
			if (!ids.isEmpty()) {
				Long n = Long.valueOf(ids.get(0).getId());
				HealthUnit us = search(n);
				listaUs.add(us);
				try {
					ids.remove(0);
				} catch (UnsupportedOperationException uoe) {
					ids = new ArrayList<HealthUnit>(ids);
					ids.remove(0);
				}
			} else {
				throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}

			// O resultado da query é navegado, e cada
			// código é informado à um método (procura) que
			// monta uma unidade de sáude a partir do código.
			while (!ids.isEmpty()) {
				HealthUnit us = new HealthUnit();
				Long n = Long.valueOf(ids.get(0).getId());

				us = search(n);
				listaUs.add(us);
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
		// O retorno desse método é uma estrutura que permite a
		// iteração nos elementos
		return new ConcreteIterator(listaUs);
	}

	public void insert(HealthUnit hu) throws RepositoryException, ObjectAlreadyInsertedException,
	ObjectNotValidException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Transaction tx = pm.currentTransaction();

		try{
			tx.begin();
			pm.makePersistent(hu);
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

	public HealthUnit search(Long code) throws RepositoryException, ObjectNotFoundException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		HealthUnit us = null;

		try{
			String query = ("select from "+HealthUnit.class.getName()+" where id == "+code+"");
			List<HealthUnit> unidade = (List<HealthUnit>)pm.newQuery(query).execute();

			us = unidade.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return us;
	}

	public void remove(Long codigo) throws RepositoryException, ObjectNotFoundException {
	}

	public HealthUnit partialSearch(Long codigo) throws RepositoryException, ObjectNotFoundException {
		HealthUnit us = null;
		long asdf = codigo;
		int hehe = (int)asdf;
		try{
			PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();

			String query = ("select from "+MedicalSpeciality.class.getName()+" where teste == "+hehe+"");
			List<MedicalSpeciality> listEsp = (List<MedicalSpeciality>)pm.newQuery(query).execute();
			List<MedicalSpeciality> specialities = new ArrayList<MedicalSpeciality>();
			
			if (!listEsp.isEmpty()) {
				MedicalSpeciality esp = specialityRep.search(listEsp.get(0).getId());
				specialities.add(esp);
				try {
					listEsp.remove(0);
				} catch (UnsupportedOperationException uoe) {
					listEsp = new ArrayList<MedicalSpeciality>(listEsp);
					listEsp.remove(0);
				}
			} else {
				//throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}

			while(!listEsp.isEmpty()){
				MedicalSpeciality ms = specialityRep.search(listEsp.get(0).getId());
				specialities.add(ms);
				listEsp.remove(0);
			}
			
			pm.close();
			
			pm = (PersistenceManager) mp.getCommunicationChannel();
			String queryUS = ("select from "+HealthUnit.class.getName()+" where teste == "+hehe+"");
			List<HealthUnit> listUs = (List<HealthUnit>)pm.newQuery(queryUS).execute();
			
			us = new HealthUnit(listUs.get(0).getDescription(), specialities);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return us;
	}
}
