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

	private static final int FOOD_COMPLAINT = 1;

	private static final int ANIMAL_COMPLAINT = 2;

	private static final int SPECIAL_COMPLAINT = 3;

	public ComplaintRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
		addressRep = new AddressRepositoryJDO(mp);
	}

	public void update(Complaint complaint) throws RepositoryException, ObjectNotFoundException,
	ObjectNotValidException {
		
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		String descricao = complaint.getDescricao();
		String email = complaint.getEmail();
		String observacao = complaint.getObservacao();
		Employee atendente = complaint.getAtendente();
		Date dataParecer = complaint.getDataParecer();
		Date dataQueixa = complaint.getDataQueixa();
		Address endereco = complaint.getEnderecoSolicitante();
		long timestamp = complaint.getTimestamp();

		try {
			pm.currentTransaction().begin();
			// Nos nao temos uma referencia para o produto selecionado.
			// Entao temos que procura-lo primeiro,
			complaint = pm.getObjectById(Complaint.class, complaint.getCodigo());
			complaint.setDescricao(descricao);
			complaint.setEmail(email);
			complaint.setObservacao(observacao);
			complaint.setAtendente(atendente);
			complaint.setDataParecer(dataParecer);
			complaint.setDataQueixa(dataQueixa);
			complaint.setEnderecoSolicitante(endereco);
			complaint.setTimestamp(timestamp);

			pm.makePersistent(complaint);
			pm.currentTransaction().commit();
		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
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
		complaint.addSolicitante(complaint.getSolicitante());

		insertFood(complaint);
	}

	private void deepInsertAnimal(AnimalComplaint complaint) throws 
	RepositoryException, ObjectAlreadyInsertedException, ObjectNotValidException {
		
		complaint.addEmail(complaint.getEmail());
		complaint.addDescricao(complaint.getDescricao());
		complaint.addEnderecoSolicitante(complaint.getEnderecoSolicitante());
		complaint.addAttendant(complaint.getAtendente());
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
		//String consulta = "select id from "+Complaint.class.getName();

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
					complaint.setSituacao(SPECIAL_COMPLAINT);
					complaint.setCodigo(special.getId());
					System.out.println(special.getId()+" = "+complaint.getCodigo()+"\n");
					
				} else if (complaint instanceof FoodComplaint) {
					FoodComplaint food = (FoodComplaint) complaint;
					deepInsertFood(food);
					complaint.setSituacao(FOOD_COMPLAINT);
					complaint.setCodigo(food.getId());
					
				} else if (complaint instanceof AnimalComplaint) {
					AnimalComplaint animal = (AnimalComplaint) complaint;
					deepInsertAnimal(animal);
					complaint.setSituacao(ANIMAL_COMPLAINT);
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
			System.out.println("Complaint ID: "+complaint.getId());
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
		pm.setDetachAllOnCommit(true);
		Complaint obj = null;
		
		Long id = null;
		String email = null;
		String descricao = null;
		Address enderecoSolicitante = null;
		String solicitante = null;
		String observation = null;
		Employee attendant = null;
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
				attendant = animal.get(0).obterAttendant();
				timestamp = animal.get(0).obterTimestamp();
			
				obj = animal.get(0);
				
			} else if(!food.isEmpty()){
				id = food.get(0).getId();
				solicitante = food.get(0).obterSolicitante();
				email = food.get(0).obterEmail();
				descricao = food.get(0).obterDescricao();
				observation = food.get(0).obterObservation();
				enderecoSolicitante = food.get(0).obterEnderecoSolicitante();
				
				obj = food.get(0);
			
			} else if (!special.isEmpty()){
				id = special.get(0).getId();
				solicitante = special.get(0).obterSolicitante();
				email = special.get(0).obterEmail();
				descricao = special.get(0).obterDescricao();
				observation = special.get(0).obterObservation();
				enderecoSolicitante = special.get(0).obterEnderecoSolicitante();

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

		List cList = new ArrayList();

		return new ConcreteIterator(cList);
	}
}
