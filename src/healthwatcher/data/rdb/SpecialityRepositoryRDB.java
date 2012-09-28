package healthwatcher.data.rdb;


import healthwatcher.data.ISpecialityRepository;
import healthwatcher.model.healthguide.MedicalSpeciality;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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





public class SpecialityRepositoryRDB implements ISpecialityRepository {

	private IPersistenceMechanism mp;

	protected ResultSet resultSet;

	public SpecialityRepositoryRDB(IPersistenceMechanism mp) {
		this.mp = mp;
	}

	public void update(MedicalSpeciality esp) throws RepositoryException, ObjectNotFoundException,
			ObjectNotValidException {
		
		if (esp != null) {
			String sql=null;
			try {
				Statement stmt = (Statement) this.mp.getCommunicationChannel();
				sql = "update scbs_especialidade set " +
                "descricao='" + esp.getDescricao() + "'" +
                " where codigo = '"+esp.getId()+"'";
				stmt.executeUpdate(sql);
				stmt.close();
			} catch (SQLException sqlException) {
				throw new SQLPersistenceMechanismException(ExceptionMessages.EXC_FALHA_BD,sql);
			} catch (PersistenceMechanismException mpException) {
				throw new RepositoryException(ExceptionMessages.EXC_FALHA_ATUALIZACAO);
			}finally {
                try {
                    mp.releaseCommunicationChannel();
                } catch (PersistenceMechanismException e) {
                    throw new RepositoryException(e.getMessage());
                }
            }
		} else {
			throw new ObjectNotValidException(ExceptionMessages.EXC_NULO);
		}
	}

	public boolean exists(Long code) throws RepositoryException {
		boolean response = false;
        String sql=null;
        try {
        	sql = "select * from scbs_especialidade where "
                + "codigo = '" + code + "'";

            Statement stmt = (Statement) this.mp.getCommunicationChannel();
            resultSet  = stmt.executeQuery(sql);
            response = resultSet.next();
            resultSet.close();
            stmt.close();            
        } catch (PersistenceMechanismException e) {
            throw new RepositoryException(e.getMessage());
        } catch (SQLException e) {
            throw new SQLPersistenceMechanismException(e.getMessage(),sql);
        }
        return response;
	}

	public IteratorDsk getSpecialityList() throws RepositoryException, ObjectNotFoundException {

		List listaEsp = new ArrayList();
		String sql = "SELECT * FROM scbs_especialidade";
		ResultSet rs = null;

		try {
			Statement stmt = (Statement) this.mp.getCommunicationChannel();
			rs = stmt.executeQuery(sql);

			if (!rs.next()) {
				throw new ObjectNotFoundException("");
			}
			do {
				MedicalSpeciality esp = search((new Long(rs.getString("codigo"))).longValue());
				listaEsp.add(esp);
			} while (rs.next());

			rs.close();
			stmt.close();
		} catch (PersistenceMechanismException e) {
			throw new RepositoryException(ExceptionMessages.EXC_FALHA_PROCURA);
		} catch (SQLException e) {
			throw new SQLPersistenceMechanismException(ExceptionMessages.EXC_FALHA_PROCURA,sql);

		} finally {
			try {
				mp.releaseCommunicationChannel();
			} catch (PersistenceMechanismException e) {
				throw new RepositoryException(e.getMessage());
			}
		}

		return new ConcreteIterator(listaEsp);
	}

	public void insert(MedicalSpeciality spec) throws RepositoryException,
			ObjectAlreadyInsertedException, ObjectNotValidException {

		if (spec != null) {
			String sql = null;
			try {
				Statement stmt = (Statement) mp.getCommunicationChannel();
				sql = "insert into scbs_especialidade (codigo,descricao) values (";
				sql += spec.getId() + ",'";
				sql += spec.getDescricao() + "')";

				stmt.executeUpdate(sql);
				stmt.close();
			} catch (SQLException e) {
				throw new SQLPersistenceMechanismException(e.getMessage(),sql);
			} catch (PersistenceMechanismException e) {
				throw new RepositoryException(e.getMessage());
			} finally {
				try {
					mp.releaseCommunicationChannel();
				} catch (PersistenceMechanismException e) {
					throw new RepositoryException(e.getMessage());
				}
			}
		} else {
			throw new ObjectNotValidException(ExceptionMessages.EXC_NULO);
		}
	}

	public MedicalSpeciality search(Long code) throws RepositoryException, ObjectNotFoundException {

		MedicalSpeciality esp = null;
		String sql = null;
		try {
			sql = "select * from scbs_especialidade where " + "codigo = '" + code + "'";

			Statement stmt = (Statement) this.mp.getCommunicationChannel();
			resultSet = stmt.executeQuery(sql);

			if (resultSet.next()) {
				esp = new MedicalSpeciality(resultSet.getString("descricao"));
				esp.setId((new Long(resultSet.getString("codigo"))).longValue());
			} else {
				throw new ObjectNotFoundException(ExceptionMessages.EXC_FALHA_PROCURA);
			}
			resultSet.close();
			stmt.close();
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
			throw new RepositoryException(ExceptionMessages.EXC_FALHA_BD);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			throw new SQLPersistenceMechanismException(ExceptionMessages.EXC_FALHA_BD,sql);
		} finally {
			try {
				mp.releaseCommunicationChannel();
			} catch (PersistenceMechanismException e) {
				throw new RepositoryException(e.getMessage());
			}
		}

		return esp;
	}

	public void remove(Long code) throws RepositoryException, ObjectNotFoundException {
	}
}
