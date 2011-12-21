package healthwatcher.data.jdo;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import lib.exceptions.ExceptionMessages;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.persistence.IPersistenceMechanism;

import healthwatcher.data.IAddressRepository;
import healthwatcher.model.address.Address;

/**
 * Repositorio responsavel por realizar a persitencia
 * de enderecos. Esse persistencia e realizada em banco
 * de dados relacional utilizando JDO
 */
public class AddressRepositoryJDO implements IAddressRepository {

	private IPersistenceMechanism mp;
	
	public AddressRepositoryJDO(IPersistenceMechanism mp) {
		this.mp = mp;
	}

	/**
	 * Metodo para atualizao de um endereco.
	 * Na versao atual do sistema disque saude
	 * essa funcionalidade nao esta implementada.
	 */
	public void update(Address end) {
	}

	/**
	 * Metodo para verificao da existencia de um
	 * endereco com codigo especificado como parametro.
	 * Na versao atual do sistema disque saude essa
	 * funcionalidade nao esta implementada.
	 */
	public boolean exists(Long codigo) {
		return false;
	}

	/**
	 * Método para inserção de endereço no repositrório persistente.
	 * Essa implementação faz a persistência através de JDBC
	 * conectando com um banco de dados relacional.
	 * @throws ObjectNotValidException 
	 * @throws RepositoryException 
	 */
	public int insert(Address end) throws ObjectNotValidException, RepositoryException {

		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		pm.setDetachAllOnCommit(true);

		// teste da validade do objeto a ser inserido
		if (end == null) {
			throw new ObjectNotValidException(ExceptionMessages.EXC_NULO);
		}
		try{
			pm.makePersistent(end);
		}finally{
			pm.close();
		}

		System.out.println(
				"O ID da nova entrada Ž: " + end.getId());

		return -1;
	}

	public Address search(Long code) throws RepositoryException {
		PersistenceManager pm = (PersistenceManager) mp.getCommunicationChannel();
		Address end = null;
		try 
		{
			end = pm.getObjectById(Address.class, code);
		}catch (JDOObjectNotFoundException e) {
			end = new Address();
		}

		return end;
	}

	public void remove(Long code){
	}

}
