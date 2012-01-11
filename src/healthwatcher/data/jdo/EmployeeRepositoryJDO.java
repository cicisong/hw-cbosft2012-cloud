package healthwatcher.data.jdo;

import java.util.List;

import healthwatcher.data.IEmployeeRepository;
import healthwatcher.model.employee.Employee;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.persistence.IPersistenceMechanism;

public class EmployeeRepositoryJDO implements IEmployeeRepository {

	private IPersistenceMechanism mp;

	public EmployeeRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
	}

	public void insert(Employee employee) throws RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();

		pm.setDetachAllOnCommit(true);
		try{
			pm.makePersistent(employee);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pm.close();
		}
	}


	public Employee search(String login) throws ObjectNotFoundException, RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Employee empregado = null;
		try{
			String query = ("select from "+Employee.class.getName()+" where login == "+login+"");
			List<Employee> employee = (List<Employee>) pm.newQuery(query).execute();
			empregado = employee.get(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return empregado;
	}

	public boolean exists(String login) throws RepositoryException {
		boolean response = false;
		PersistenceManager pm = (PersistenceManager) this.mp.getCommunicationChannel();
		Query q = pm.newQuery("SELECT FROM "+Employee.class.getName()+" where login == "+login+"");
		try {
			List<Employee> ids = (List<Employee>) q.execute();
			
			if (ids.size() > 0){
				response = true;
			}
			pm.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}

	public void update(Employee employee) throws ObjectNotFoundException, ObjectNotValidException, RepositoryException {

	}

	public void remove(String login) throws ObjectNotFoundException {

	}
}
