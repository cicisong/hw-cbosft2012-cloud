package healthwatcher.model.healthguide;

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
public class MedicalSpeciality implements java.io.Serializable, Subject {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String descricao;

	@Persistent
	private int teste;
	
	//#if relacional
//@	private List subscribers = new ArrayList();
	//#endif
	
	public MedicalSpeciality(String descricao) {
		this.descricao = descricao;
	}
	
	public void setCode(int codigo){	
		this.teste = codigo;
	}
	
	public int getCode(){
		return this.teste;
	}
	
	public void setId(Long code){
		this.id = code;
	}
	
	public Long getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
		//#if relacional
//@		notifyObservers(); //thiago alterou aqui
		//#endif
		
	}

	public String toString() {
		return descricao;
	}
	
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
}
