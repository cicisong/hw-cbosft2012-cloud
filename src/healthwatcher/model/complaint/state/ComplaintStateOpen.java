package healthwatcher.model.complaint.state;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.Complaint;
import healthwatcher.model.complaint.Situation;
import healthwatcher.model.employee.Employee;
import lib.util.Date;


@SuppressWarnings("serial")
public class ComplaintStateOpen extends ComplaintState {
	
	public ComplaintStateOpen(){
		super();
	}

	public ComplaintStateOpen(Long codigo,String solicitante, String descricao,
            String observacao, String email, Employee atendente,
            Date dataParecer, Date dataQueixa,
            Address enderecoSolicitante, long timestamp) {
		super(codigo,solicitante, descricao,
                observacao, email, atendente,
                dataParecer, dataQueixa,
                enderecoSolicitante, timestamp);
	}
	public void setAttendant(Employee atend, Complaint complaint) {
        attendant = atend;
        complaint.notifyObservers();
    }
    public void setCode(Long cod) {
        code = cod;
    }
    public void setMedicalOpinionDate(Date data) {
        medicalOpinionDate = data;
    }
    public void setComplaintDate(Date data) {
        complaintDate = data;
    }
    public void setDescription(String desc) {
        description = desc;
    }
    public void setEmail(String _email) {
        email = _email;
    }
    public void setComplainerAddress(Address end) {
        complainerAddress = end;
    }
    public void setObservation(String obs, Complaint complaint) {
        observation = obs;
        complaint.notifyObservers();
    }
    public void setComplainer(String _solicitante) {
        complainer = _solicitante;
    }
    public void setTimestamp(long newTimestamp) {
    	timestamp = newTimestamp;
    }
	public int getStatus() {
		return Situation.QUEIXA_ABERTA;
	}
}
