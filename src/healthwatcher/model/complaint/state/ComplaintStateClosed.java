package healthwatcher.model.complaint.state;

import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.Complaint;
import healthwatcher.model.complaint.Situation;
import healthwatcher.model.employee.Employee;
import lib.util.Date;


@SuppressWarnings("serial")
public class ComplaintStateClosed extends ComplaintState  {
	
	public ComplaintStateClosed(){
	}

	public ComplaintStateClosed(Long codigo,String solicitante, String descricao,
            String observacao, String email, Employee atendente,
            Date dataParecer, Date dataQueixa,
            Address enderecoSolicitante, long timestamp) {
		super(codigo,solicitante, descricao,
                observacao, email, atendente,
                dataParecer, dataQueixa,
                enderecoSolicitante, timestamp);
	}
	public void setAttendant(Employee atend, Complaint complaint) {
    }
    public void setCode(Long cod) {
    	System.out.println("fechada: "+cod);
    }
    public void setMedicalOpinionDate(Date data) {
    }
    public void setComplaintDate(Date data) {
    }
    public void setDescription(String desc) {
    }
    public void setEmail(String _email) {
    }
    public void setComplainerAddress(Address end) {
    }
    public void setObservation(String obs, Complaint complaint) {
    }
    public void setSituacao(int sit) {
    }
    public void setComplainer(String _solicitante) {
    }
    public void setTimestamp(long newTimestamp) {
    }
	public int getStatus() {
		return Situation.QUEIXA_FECHADA;
	}

}
