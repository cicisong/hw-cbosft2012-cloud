package healthwatcher.model.complaint;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class Symptom implements java.io.Serializable, Subject {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String description;
	
	@Persistent
	private int teste; //codigo pra linkar a classe diseaseType

	//#if relacional
	private List subscribers = new ArrayList();
	//#endif
	
	public Symptom(String descricao) {
		this.description = descricao;
	}
	
	public void setCode(int codigo){	
		this.teste = codigo;
	}
	
	public int getCode(){
		return this.teste;
	}

	public Long getId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setId(Long codigo) {
		this.id = codigo;
	}
	
	public void setDescription(String description) {
		this.description = description;
		//#if relacional
		notifyObservers();
		//#endif
	}

	

	//#if norelacional
//@	@Override
//@	public void addObserver(Observer observer) {
//@		// TODO Auto-generated method stub
//@		
//@	}
//@
//@	@Override
//@	public void removeObserver(Observer observer) {
//@		// TODO Auto-generated method stub
//@		
//@	}
//@
//@	@Override
//@	public void notifyObservers() {
//@		// TODO Auto-generated method stub
//@		
//@	}
	//#endif

	//#if relacional
	public void addObserver(Observer observer) {
		subscribers.add(observer);
	}

	public void removeObserver(Observer observer) {
		subscribers.remove(observer);
	}

	public void notifyObservers() {
		for (Iterator it = subscribers.iterator(); it.hasNext();) {
			Observer observer = (Observer) it.next();
			try {
				observer.notify(this);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (ObjectNotValidException e) {
				e.printStackTrace();
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			} catch (TransactionException e) {
				e.printStackTrace();
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		}
	}
	//#endif
}
