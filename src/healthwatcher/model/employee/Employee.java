package healthwatcher.model.employee;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.patterns.observer.Observer;
import lib.patterns.observer.Subject;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Employee implements Serializable, Subject {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String name;

	@Persistent
	private String login;

	@Persistent
	private String password;
	
	//#if relacional
//@	private List subscribers = new ArrayList();
	//#endif

	public Employee(String login, String password, String name) {
		this.name = name;
		this.login = login;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		
		//#if relacional
//@		notifyObservers();
		//#endif
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
		//#if relacional
//@		notifyObservers();
		//#endif
	}

	public void setPassword(String password) {
		this.password = password;
		//#if relacional
//@		notifyObservers();
		//#endif
	}

	public String getPassword() {
		return password;
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

	//#if norelacional
	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}
	//#endif
	
	//#if relacional
//@	public void addObserver(Observer observer) {
//@		subscribers.add(observer);
//@	}
//@
//@	public void removeObserver(Observer observer) {
//@		subscribers.remove(observer);
//@	}
//@
//@	public void notifyObservers() {
//@		for (Iterator it = subscribers.iterator(); it.hasNext();) {
//@			Observer observer = (Observer) it.next();
//@			try {
//@				observer.notify(this);
//@			} catch (RemoteException e) {
//@				e.printStackTrace();
//@			} catch (ObjectNotValidException e) {
//@				e.printStackTrace();
//@			} catch (ObjectNotFoundException e) {
//@				e.printStackTrace();
//@			} catch (TransactionException e) {
//@				e.printStackTrace();
//@			} catch (RepositoryException e) {
//@				e.printStackTrace();
//@			}
//@		}
//@	} 
	//#endif
}
