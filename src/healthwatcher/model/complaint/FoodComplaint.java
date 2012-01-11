package healthwatcher.model.complaint;

import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.state.FoodComplaintState;
import healthwatcher.model.complaint.state.FoodComplaintStateClosed;
import healthwatcher.model.complaint.state.FoodComplaintStateOpen;
import healthwatcher.model.employee.Employee;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import lib.util.Date;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FoodComplaint extends Complaint {

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
	private FoodComplaintState state;

	//construtor vazio
	public FoodComplaint() {
		super();
		state= new FoodComplaintStateOpen();
	}

	public FoodComplaint(String solicitante, String descricao, String observacao, String email,
			Employee atendente, int situacao, Date dataParecer, Date dataQueixa,
			Address enderecoSolicitante, int qtdeComensais, int qtdeDoentes, int qtdeInternacoes,
			int qtdeObitos, String localAtendimento, String refeicaoSuspeita, Address enderecoDoente) {

		//inicializar tipo da queixa
		   super(solicitante, descricao, observacao,email, atendente, situacao, dataParecer,dataQueixa, enderecoSolicitante,0);
	        if(situacao==Situation.QUEIXA_ABERTA)
	            state= new FoodComplaintStateOpen(qtdeComensais,qtdeDoentes, qtdeInternacoes, qtdeObitos,localAtendimento,
	                    refeicaoSuspeita,enderecoDoente);
	            else if(situacao==Situation.QUEIXA_FECHADA)
	            	state= new FoodComplaintStateClosed(qtdeComensais,qtdeDoentes, qtdeInternacoes, qtdeObitos,localAtendimento,
	                        refeicaoSuspeita,enderecoDoente);
	}

	public void setSituacao(int situacao) {
		super.setSituacao(situacao);
		state.setStatus(situacao, this);
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
	
	public void addEnderecoSolicitante(Address endereco){
		this.complainerAddress = endereco;
	}
	
	public Address obterEnderecoSolicitante(){
		return this.complainerAddress;
	}
	
	/*public void addAttendant(Employee atendente){
		this.attendant = atendente;
	}

	public Employee obterAttendant() {
		return this.attendant;
	}*/

	
	public Address getEnderecoDoente() {
		return state.getEnderecoDoente();
	}

	public void setEnderecoDoente(Address enderecoDoente) {
		state.setEnderecoDoente(enderecoDoente);
	}

	public String getLocalAtendimento() {
		return state.getLocalAtendimento();
	}

	public void setLocalAtendimento(String localAtendimento) {
		state.setLocalAtendimento(localAtendimento);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQtdeComensais() {
		return state.getQtdeComensais();
	}

	public void setQtdeComensais(int qtdeComensais) {
		state.setQtdeComensais(qtdeComensais);
	}

	public int getQtdeDoentes() {
		return state.getQtdeDoentes();
	}

	public void setQtdeDoentes(int qtdeDoentes) {
		state.setQtdeDoentes(qtdeDoentes);
	}

	public int getQtdeInternacoes() {
		return state.getQtdeInternacoes();
	}

	public void setQtdeInternacoes(int qtdeInternacoes) {
		state.setQtdeInternacoes(qtdeInternacoes);
	}

	public int getQtdeObitos() {
		return state.getQtdeObitos();
	}

	public void setQtdeObitos(int qtdeObitos) {
		state.setQtdeObitos(qtdeObitos);
	}

	public String getRefeicaoSuspeita() {
		return state.getRefeicaoSuspeita();
	}

	public void setRefeicaoSuspeita(String refeicaoSuspeita) {
		state.setRefeicaoSuspeita(refeicaoSuspeita);
	}
    public void setComplaintState(FoodComplaintState _state){
    	state= _state;
    }
}
