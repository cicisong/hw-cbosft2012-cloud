package healthwatcher.data.jdo;

import healthwatcher.data.IComplaintRepository;
import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.AnimalComplaint;
import healthwatcher.model.complaint.Complaint;
import healthwatcher.model.complaint.FoodComplaint;
import healthwatcher.model.complaint.SpecialComplaint;
import healthwatcher.model.employee.Employee;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import lib.exceptions.ExceptionMessages;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.PersistenceMechanismException;
import lib.exceptions.RepositoryException;
import lib.persistence.IPersistenceMechanism;
import lib.util.ConcreteIterator;
import lib.util.Date;
import lib.util.IteratorDsk;


public class ComplaintRepositoryJDO implements IComplaintRepository {

	private IPersistenceMechanism mp;

	private AddressRepositoryJDO addressRep;
	private EmployeeRepositoryJDO employeeRep;

	private static final int FOOD_COMPLAINT = 1;

	private static final int ANIMAL_COMPLAINT = 2;

	private static final int SPECIAL_COMPLAINT = 3;

	public ComplaintRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
		addressRep = new AddressRepositoryJDO(mp);
		employeeRep = new EmployeeRepositoryJDO(mp);
	}

	public void update(Complaint complaint) throws RepositoryException, ObjectNotFoundException,
	ObjectNotValidException {

		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		try {
			if (complaint instanceof SpecialComplaint) {
				SpecialComplaint special = pm.getObjectById(SpecialComplaint.class, complaint.getCodigo());
				special.setObservacao(complaint.getObservacao());
				special.setAtendente(complaint.getAtendente());
				special.setDataParecer(complaint.getDataParecer());
				special.setSituacao(complaint.getSituacao());
			}else if (complaint instanceof FoodComplaint){
				FoodComplaint food =  pm.getObjectById(FoodComplaint.class, complaint.getCodigo());
				food.setObservacao(complaint.getObservacao());
				food.setAtendente(complaint.getAtendente());
				food.setDataParecer(complaint.getDataParecer());
				food.setSituacao(complaint.getSituacao());
			}else if (complaint instanceof AnimalComplaint){
				AnimalComplaint animal = pm.getObjectById(AnimalComplaint.class, complaint.getCodigo());
				animal.setObservacao(complaint.getObservacao());
				animal.setAtendente(complaint.getAtendente());
				animal.setDataParecer(complaint.getDataParecer());
				animal.setSituacao(complaint.getSituacao());
			}
			
		} finally {
			pm.close();
		}
	}

	private void deepInsertFood(FoodComplaint complaint) throws 
	RepositoryException, ObjectAlreadyInsertedException, ObjectNotValidException {

		complaint.addEmail(complaint.getEmail());
		complaint.addDescricao(complaint.getDescricao());
		complaint.addObservation(complaint.getObservacao());
		complaint.addEnderecoSolicitante(complaint.getEnderecoSolicitante());
		//complaint.addAttendant(complaint.getAtendente());
		complaint.addSolicitante(complaint.getSolicitante());

		insertFood(complaint);
	}

	private void deepInsertAnimal(AnimalComplaint complaint) throws 
	RepositoryException, ObjectAlreadyInsertedException, ObjectNotValidException {

		complaint.addEmail(complaint.getEmail());
		complaint.addDescricao(complaint.getDescricao());
		complaint.addEnderecoSolicitante(complaint.getEnderecoSolicitante());
		//complaint.addAttendant(complaint.getAtendente());
		complaint.addObservation(complaint.getObservacao());
		complaint.addSolicitante(complaint.getSolicitante());
		complaint.addTimestamp(complaint.getTimestamp());

		insertAnimal(complaint);
	}

	private void deepInsertSpecial(SpecialComplaint complaint) throws 
	RepositoryException, ObjectAlreadyInsertedException, ObjectNotValidException {

		complaint.addEmail(complaint.getEmail());
		complaint.addDescricao(complaint.getDescricao());
		complaint.addObservation(complaint.getObservacao());
		complaint.addEnderecoSolicitante(complaint.getEnderecoSolicitante());
		//complaint.addAttendant(complaint.getAtendente());
		complaint.addSolicitante(complaint.getSolicitante());

		insertSpecial(complaint);
	}

	private void deepInsertCommon(Complaint complaint) throws ObjectAlreadyInsertedException,
	RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		if (complaint instanceof SpecialComplaint) {
			complaint.setSituacao(SPECIAL_COMPLAINT);
		} else if (complaint instanceof FoodComplaint) {
			complaint.setSituacao(FOOD_COMPLAINT);
		} else if (complaint instanceof AnimalComplaint) {
			complaint.setSituacao(ANIMAL_COMPLAINT);
		}

		try{
			pm.makePersistent(complaint);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pm.close();
		}
	}

	/*private void deepInsertCommon(Complaint complaint) throws ObjectAlreadyInsertedException,
	RepositoryException, ObjectNotValidException {

		if (complaint.getEnderecoSolicitante() != null) {
			System.out.println("Inserindo endereco solicitante!!!");
			addressRep.insert(complaint.getEnderecoSolicitante());
		}

		int complaintType = -1;
		if (complaint instanceof SpecialComplaint) {
			System.out.println("E uma instancia de SpecialComplaint?");
			SpecialComplaint special = (SpecialComplaint) complaint;
			complaintType = SPECIAL_COMPLAINT;
			complaint.setSituacao(complaintType);
			deepInsertSpecial(special);


		} else if (complaint instanceof FoodComplaint) {
			System.out.println("E uma instancia de FoodComplaint?");
			FoodComplaint food = (FoodComplaint) complaint;
			complaintType = FOOD_COMPLAINT;
			complaint.setSituacao(complaintType);
			deepInsertFood(food);

		} else if (complaint instanceof AnimalComplaint) {
			System.out.println("E uma instancia de AnimalComplaint?");
			AnimalComplaint animal = (AnimalComplaint) complaint;
			complaintType = ANIMAL_COMPLAINT;
			complaint.setSituacao(complaintType);
			deepInsertAnimal(animal);
			complaint.setCodigo(animal.getId());
		}


		//pm.setDetachAllOnCommit(true);
		//pm.makePersistent(complaint);
		System.out.println("Persistiu o objeto na funcao deepInsertCommon!!!");



	}*/

	public boolean exists(Long code) throws RepositoryException {
		boolean response = false;

		return response;
	}

	public Long insert(Complaint complaint) throws ObjectAlreadyInsertedException,
	RepositoryException, ObjectNotValidException {
		try {
			if (complaint != null) {

				deepInsertCommon(complaint);

				if (complaint instanceof SpecialComplaint) {
					SpecialComplaint special = (SpecialComplaint) complaint;
					deepInsertSpecial(special);
					complaint.setSituacao(1);
					complaint.setCodigo(special.getId());

				} else if (complaint instanceof FoodComplaint) {
					FoodComplaint food = (FoodComplaint) complaint;
					deepInsertFood(food);
					complaint.setSituacao(1);
					complaint.setCodigo(food.getId());

				} else if (complaint instanceof AnimalComplaint) {
					AnimalComplaint animal = (AnimalComplaint) complaint;
					deepInsertAnimal(animal);
					complaint.setSituacao(1);
					complaint.setCodigo(animal.getId());
				}
			} else {
				throw new ObjectNotValidException(ExceptionMessages.EXC_NULO);
			}
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new RepositoryException(ExceptionMessages.EXC_FALHA_INCLUSAO);
		}
		updateTimestamp(complaint.getTimestamp() + "", "SCBS_queixa", complaint.getCodigo() + "");
		return complaint.getCodigo();
	}

	private void insertFood(FoodComplaint complaint) throws RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Transaction tx = pm.currentTransaction();

		//pm.setDetachAllOnCommit(true);
		try{
			// Start the transaction
			tx.begin();
			pm.makePersistent(complaint);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (tx.isActive()){
				// Error occurred so rollback the transaction
				tx.rollback();
			}
			pm.close();
		}
	}

	private void insertAnimal(AnimalComplaint complaint) throws RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Transaction tx = pm.currentTransaction();

		//pm.setDetachAllOnCommit(true);
		try{
			// Start the transaction
			tx.begin();
			pm.makePersistent(complaint);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (tx.isActive()){
				// Error occurred so rollback the transaction
				tx.rollback();
			}
			pm.close();
		}
	}

	private void insertSpecial(SpecialComplaint complaint) throws RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Transaction tx = pm.currentTransaction();

		//pm.setDetachAllOnCommit(true);
		try{
			// Start the transaction
			tx.begin();
			pm.makePersistent(complaint);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (tx.isActive()){
				// Error occurred so rollback the transaction
				tx.rollback();
			}
			pm.close();
		}
	}

	/*public Complaint search(Long code) throws RepositoryException, ObjectNotFoundException {
		System.out.println("Inicio da funcao search!!");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.setDetachAllOnCommit(true);

		String query = ("select from "+AnimalComplaint.class.getName());
		Complaint q = null;
		try{
			System.out.println("passou antes da construcao da lista!!!");
			List<AnimalComplaint> animal = (List<AnimalComplaint>)pm.newQuery(query).execute();
			System.out.println("passou depois da lista!!!");

			q = animal.get(0);//accessAnimal(code);
			System.out.println("Email: "+animal.get(0).obterEmail());

			// Dependendo do tipo da queixa o acesso aos dados é feito
			// por um método específico
			switch (tipoQueixa) {

			case Complaint.QUEIXA_ALIMENTAR:
				System.out.println("Eh Queixa Alimentar?");
				q = accessFood(code);
				break;

			case Complaint.QUEIXA_ANIMAL:
				System.out.println("Eh queixa animal?");
				q = accessAnimal(code);
				break;

			case Complaint.QUEIXA_DIVERSA:
				System.out.println("Eh queixa diversa?");
				q = accessSpecial(code);
				break;

			default:
				throw new IllegalArgumentException();
			}
			q.setCodigo(animal.get(0).getId());
			q.setEmail(animal.get(0).obterEmail());
			//q.setAtendente(atendente);
			//q.setComplaintState(_state);
			//q.setDataParecer(dataParecer);
			//q.setDataQueixa(dataQueixa);
			q.setDescricao(animal.get(0).obterDescricao());
			//q.setEnderecoSolicitante(animal.get(0).obterEnderecoSolicitante());
			//q.setObservacao(observacao);
			//q.setSituacao(situacao);
			//q.setSolicitante(solicitante);
			//q.setTimestamp(timestamp);

		} catch(Exception e) {
			e.printStackTrace();
		}
		long timestamp = searchTimestamp("SCBS_queixa", q.getCodigo() + "");
		q.setTimestamp(timestamp);
		return q;
	}
	 */
	public Complaint search(Long code) throws RepositoryException, ObjectNotFoundException {

		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		//pm.setDetachAllOnCommit(true);
		Complaint obj = null;

		Long id = null;
		String email = null;
		String descricao = null;
		Address enderecoSolicitante = null;
		String solicitante = null;
		String observation = null;
		Employee attendant = null;
		int situacao = 1;
		long timestamp = 0;

		try{

			String queryFood = ("select from "+FoodComplaint.class.getName()+" where id == "+code+"");
			List<FoodComplaint> food = (List<FoodComplaint>) pm.newQuery(queryFood).execute();

			String queryAnimal = ("select from "+AnimalComplaint.class.getName()+" where id == "+code+"");
			List<AnimalComplaint> animal = (List<AnimalComplaint>) pm.newQuery(queryAnimal).execute();

			String querySpecial = ("select from "+SpecialComplaint.class.getName()+" where id == "+code+"");
			List<SpecialComplaint> special = (List<SpecialComplaint>) pm.newQuery(querySpecial).execute();

			if (!animal.isEmpty()){
				id = animal.get(0).getId();
				email = animal.get(0).obterEmail();
				descricao = animal.get(0).obterDescricao();
				enderecoSolicitante = animal.get(0).obterEnderecoSolicitante();
				solicitante = animal.get(0).obterSolicitante();
				observation = animal.get(0).obterObservation();
				//attendant = animal.get(0).obterAttendant();
				timestamp = animal.get(0).obterTimestamp();
				situacao = animal.get(0).getSituacao();

				obj = animal.get(0);

			} else if(!food.isEmpty()){
				id = food.get(0).getId();
				solicitante = food.get(0).obterSolicitante();
				email = food.get(0).obterEmail();
				descricao = food.get(0).obterDescricao();
				observation = food.get(0).obterObservation();
				enderecoSolicitante = food.get(0).obterEnderecoSolicitante();
				//attendant = food.get(0).obterAttendant();
				situacao = food.get(0).getSituacao();

				obj = food.get(0);

			} else if (!special.isEmpty()){
				id = special.get(0).getId();
				solicitante = special.get(0).obterSolicitante();
				email = special.get(0).obterEmail();
				descricao = special.get(0).obterDescricao();
				observation = special.get(0).obterObservation();
				enderecoSolicitante = special.get(0).obterEnderecoSolicitante();
				//attendant = special.get(0).obterAttendant();
				situacao = special.get(0).getSituacao();

				obj = special.get(0);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

		obj.setCodigo(id);
		obj.setEmail(email);
		obj.setDescricao(descricao);
		obj.setEnderecoSolicitante(enderecoSolicitante);
		obj.setSolicitante(solicitante);
		obj.setObservacao(observation);
		obj.setAtendente(attendant);
		obj.setTimestamp(timestamp);
		obj.setSituacao(situacao);

		return obj;

	}

	public void remove(Long codigo) throws RepositoryException, ObjectNotFoundException {
	}

	private void updateTimestamp(String value, String tableName, String id) {

	}

	private long searchTimestamp(String tableName, String id) {
		long answer = 0;

		return answer;
	}

	public IteratorDsk getComplaintList() throws ObjectNotFoundException, RepositoryException {
		List<Complaint> cList = new ArrayList<Complaint>();
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();		

		Complaint complaint = null;

		try {

			String queryFood = ("select id from "+FoodComplaint.class.getName());
			List<Long> food = (List<Long>) pm.newQuery(queryFood).execute();

			String queryAnimal = ("select id from "+AnimalComplaint.class.getName());
			List<Long> animal = (List<Long>) pm.newQuery(queryAnimal).execute();

			String querySpecial = ("select id from "+SpecialComplaint.class.getName());
			List<Long> special = (List<Long>) pm.newQuery(querySpecial).execute();

			if (food.isEmpty() && animal.isEmpty() && special.isEmpty()) {
				throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}

			if(!food.isEmpty()){
				complaint = search(food.get(0));
				cList.add(complaint);
				try {
					food.remove(0);
				} catch (UnsupportedOperationException uoe) {
					food = new ArrayList<Long>(food);
					food.remove(0);
				}
				
				while(!food.isEmpty()){
					complaint = search (food.get(0));
					cList.add(complaint);
					food.remove(0);
				}
			}
			
			if(!animal.isEmpty()){
				complaint = search(animal.get(0));
				cList.add(complaint);
				try {
					animal.remove(0);
				} catch (UnsupportedOperationException uoe) {
					animal = new ArrayList<Long>(animal);
					animal.remove(0);
				}
				
				while(!animal.isEmpty()){
					complaint = search (animal.get(0));
					cList.add(complaint);
					animal.remove(0);
				}
			}
			
			if(!special.isEmpty()){
				complaint = search(special.get(0));
				cList.add(complaint);
				try {
					special.remove(0);
				} catch (UnsupportedOperationException uoe) {
					special = new ArrayList<Long>(special);
					special.remove(0);
				}
				
				while(!special.isEmpty()){
					complaint = search (special.get(0));
					cList.add(complaint);
					special.remove(0);
				}
			}
			
			pm.close();
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new RepositoryException("PersistenceMechanismException: " + e.getMessage());
		}

		return new ConcreteIterator(cList);
	}
}
