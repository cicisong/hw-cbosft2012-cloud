package healthwatcher.data.jdo;

import healthwatcher.data.IDiseaseRepository;
import healthwatcher.model.complaint.DiseaseType;
import healthwatcher.model.complaint.Symptom;

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

public class DiseaseTypeRepositoryJDO implements IDiseaseRepository {

	private IPersistenceMechanism mp;

	private SymptomRepositoryJDO symptomRep;

	public DiseaseTypeRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
		this.symptomRep = new SymptomRepositoryJDO(mp);
	}

	public void update(DiseaseType td) throws RepositoryException, ObjectNotFoundException,
	ObjectNotValidException {
	}

	public void filter(Long codigo) throws RepositoryException, ObjectNotFoundException {
	}

	public boolean exists(Long code) throws RepositoryException {
		boolean response = false;
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		long asdf = code;
		int hehe = (int)asdf;
		Query q = pm.newQuery("SELECT FROM "+DiseaseType.class.getName()+" where teste == "+hehe+"");
		try {
			List<DiseaseType> ids = (List<DiseaseType>) q.execute();

			if (ids.size() > 0){
				response = true;
			}
			pm.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}

	public IteratorDsk getDiseaseTypeList() throws RepositoryException, ObjectNotFoundException {
		List<DiseaseType> listatd = new ArrayList<DiseaseType>();		
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();

		// Query para selecionar os códigos de todas unidades de saúde
		// existentes no sistema
		Query q = pm.newQuery("SELECT id FROM "+DiseaseType.class.getName());

		try {
			List<Long> ids = (List<Long>) q.execute();
			// O resultado da query é testado para saber
			// da existência de unidades de saúde cadastradas.
			// Caso não existam uma exceção é lançada.
			if (!ids.isEmpty()) {
				DiseaseType us = partialSearch(ids.get(0));
				listatd.add(us);
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
				DiseaseType us = new DiseaseType();
				us = partialSearch(ids.get(0));
				listatd.add(us);
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
		return new ConcreteIterator(listatd);
	}

	public void insert(DiseaseType td) throws RepositoryException, ObjectAlreadyInsertedException,
	ObjectNotValidException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Transaction tx = pm.currentTransaction();
		if (td != null) {

			try{
				tx.begin();
				pm.makePersistent(td);
				tx.commit();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if (tx.isActive()){
					tx.rollback();
				}
				pm.close();
			}
		}else{
			throw new ObjectNotValidException(ExceptionMessages.EXC_NULO);
		}
	}

	/**
	 * Método para recuperar um tipo de doença do banco de dados.
	 *
	 * @param codigo código do tipo de doença a ser procurado
	 * @return um objeto tipo doença montado a partir dos dados
	 *           do banco de dados
	 * @throws RepositoryException 
	 */
	public DiseaseType partialSearch(Long codigo) throws ObjectNotFoundException, RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		DiseaseType td = null;

		try{
			String query = ("select from "+DiseaseType.class.getName()+" where id == "+codigo+"");
			List<DiseaseType> unidade = (List<DiseaseType>)pm.newQuery(query).execute();

			td = unidade.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return td;
	}

	/**
	 * Método para recuperar um tipo de doença do banco de dados.
	 *
	 * @param code código do tipo de doença a ser procurado
	 * @return um objeto tipo doença montado a partir dos dados
	 *		   do banco de dados
	 */
	public DiseaseType search(Long code) throws RepositoryException, ObjectNotFoundException {
		DiseaseType td = null;
		long asdf = code;
		int hehe = (int)asdf;
		String name, description, manifestation, duration;
		List<Symptom> symptoms;
		
		try{
			PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();

			String queryUS = ("select from "+DiseaseType.class.getName()+" where teste == "+hehe+"");
			List<DiseaseType> listUs = (List<DiseaseType>)pm.newQuery(queryUS).execute();

			if (!listUs.isEmpty()){
				name = listUs.get(0).getName();
				description = listUs.get(0).getDescription();
				manifestation = listUs.get(0).getManifestation();
				duration = listUs.get(0).getDuration();
			}else{
				throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}


			pm.close();

			pm = (PersistenceManager) mp.getCommunicationChannel();
			String query = ("select from "+Symptom.class.getName()+" where teste == "+hehe+"");
			List<Symptom> listSym = (List<Symptom>)pm.newQuery(query).execute();
			List<Symptom> listSt = new ArrayList<Symptom>();

			if (!listSym.isEmpty()) {
				Symptom esp = symptomRep.search(listSym.get(0).getId());
				listSt.add(esp);
				try {
					listSym.remove(0);
				} catch (UnsupportedOperationException uoe) {
					listSym = new ArrayList<Symptom>(listSym);
					listSym.remove(0);
				}
			} else {
				//throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}

			while(!listSym.isEmpty()){
				Symptom ms = symptomRep.search(listSym.get(0).getId());
				listSt.add(ms);
				listSym.remove(0);
			}


			symptoms = listSt;
			td = new DiseaseType(name, description, manifestation, duration, symptoms);

		}catch(Exception e){
			e.printStackTrace();
		}
		return td;
	}
}