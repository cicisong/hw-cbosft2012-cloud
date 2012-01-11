package healthwatcher.model.complaint;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.state.SpecialComplaintState;
import healthwatcher.model.complaint.state.SpecialComplaintStateClosed;
import healthwatcher.model.complaint.state.SpecialComplaintStateOpen;
import healthwatcher.model.employee.Employee;
import lib.util.Date;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class SpecialComplaint extends Complaint {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String solicitante;
	
    @Persistent
    private String email;
    
    @Persistent
	private String observation;
	
    @Persistent(serialized = "true")
	private Address complainerAddress;
    
	//private Employee attendant;
    
    @Persistent
    private String description; 

	@Persistent(serialized = "true")
    private SpecialComplaintState state;

	//construtor vazio
    public SpecialComplaint() {
    	super();
    	state= new SpecialComplaintStateOpen();
    }

	public SpecialComplaint(String solicitante, String descricao, String observacao, String email,
			Employee atendente, int situacao, Date dataParecer, Date dataQueixa,
			Address enderecoSolicitante, short idade, String instrucao, String ocupacao,
			Address enderecoOcorrencia) {

		//inicializar tambem o tipo da queixa
	     super(solicitante, descricao, observacao,email, atendente, situacao, dataParecer,dataQueixa, enderecoSolicitante,0);

	        if(situacao==Situation.QUEIXA_ABERTA)
	            state= new SpecialComplaintStateOpen(idade,instrucao, ocupacao,enderecoOcorrencia);
	            else if(situacao==Situation.QUEIXA_FECHADA)
	            	state= new SpecialComplaintStateClosed(idade,instrucao, ocupacao,enderecoOcorrencia);
	}

	public void setSituacao(int situacao) {
		super.setSituacao(situacao);
		state.setStatus(situacao, this);
	}
	
	public void setId(Long code){
		this.id = code;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void addEmail(String email){
		this.email = email;
	}
	
	public String obterEmail(){
		return this.email;
	}
	
	public void addSolicitante(String solicitante_){
		this.solicitante = solicitante_;
	}
	
	public String obterSolicitante(){
		return this.solicitante;
	}
	
	public void addObservation(String observation_){
		this.observation = observation_;
	}
	
	public String obterObservation(){
		return this.observation;
	}
	
	public void addDescricao(String descricao){
		this.description = descricao;
	}
	
	public String obterDescricao(){
		return this.description;
	}
	
	/*public void addAttendant(Employee atendente){
		this.attendant = atendente;
	}

	public Employee obterAttendant() {
		return this.attendant;
	}*/
	
	public void addEnderecoSolicitante(Address endereco){
		this.complainerAddress = endereco;
	}
	
	public Address obterEnderecoSolicitante(){
		return this.complainerAddress;
	}
	
	public Address getEnderecoOcorrencia() {
		return state.getEnderecoOcorrencia();
	}

	public void setEnderecoOcorrencia(Address enderecoOcorrencia) {
		state.setEnderecoOcorrencia(enderecoOcorrencia);
	}

	public short getIdade() {
		return state.getIdade();
	}

	public void setIdade(short idade) {
		state.setIdade(idade);
	}

	public String getInstrucao() {
		return state.getInstrucao();
	}

	public void setInstrucao(String instrucao) {
		state.setInstrucao(instrucao);
	}

	public String getOcupacao() {
		return state.getOcupacao();
	}

	public void setOcupacao(String ocupacao) {
		state.setOcupacao(ocupacao);
	}
	public void setComplaintState(SpecialComplaintState _state){
    	state= _state;
    }
}