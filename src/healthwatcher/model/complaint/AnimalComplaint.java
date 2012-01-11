package healthwatcher.model.complaint;

import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.state.AnimalComplaintState;
import healthwatcher.model.complaint.state.AnimalComplaintStateClosed;
import healthwatcher.model.complaint.state.AnimalComplaintStateOpen;
import healthwatcher.model.employee.Employee;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import lib.util.Date;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class AnimalComplaint extends Complaint {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent(serialized = "true")
	private AnimalComplaintState state;

	@Persistent
	private String email;

	@Persistent
	private String solicitante;

	@Persistent
	private String observation;

	//Persistent(serialized = "true")
	//private Employee attendant;

	@Persistent
	private long timestamp;

	@Persistent(serialized = "true")
	private Address complainerAddress;

	@Persistent
	private String description; 

	public AnimalComplaint() {
		super();
		state= new AnimalComplaintStateOpen();
	}
	public AnimalComplaint(String solicitante, String descricao, String observacao, String email,
			Employee atendente, int situacao, Date dataParecer, Date dataQueixa,
			Address enderecoSolicitante, short animalQuantity, Date inconvenienceDate,
			String animal, Address occurenceLocalAddress) {

		// inicializar tipo da queixa
		super(solicitante, descricao, observacao,
				email, atendente, situacao, dataParecer,
				dataQueixa, enderecoSolicitante,0);

		if(situacao==Situation.QUEIXA_ABERTA)
			state= new AnimalComplaintStateOpen(animalQuantity, inconvenienceDate, animal, occurenceLocalAddress);
		else if(situacao==Situation.QUEIXA_FECHADA)
			state= new AnimalComplaintStateClosed(animalQuantity, inconvenienceDate, animal, occurenceLocalAddress);
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

	public void addTimestamp(long timestamp_){
		this.timestamp = timestamp_;
	}

	public long obterTimestamp(){
		return this.timestamp;
	}

	public void addObservation(String observation_){
		this.observation = observation_;
	}

	public String obterObservation(){
		return this.observation;
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

	public void addDescricao(String descricao){
		this.description = descricao;
	}

	public String obterDescricao(){
		return this.description;
	}

	public void setSituacao(int situacao) {
		super.setSituacao(situacao);
		state.setStatus(situacao, this);
	}

	public void setComplaintState(AnimalComplaintState _state){
		state= _state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnimal() {
		return state.getAnimal();
	}

	public void setAnimal(String animal) {
		state.setAnimal(animal);
	}

	public short getAnimalQuantity() {
		return state.getQtdeAnimais();
	}

	public void setAnimalQuantity(short animalQuantity) {
		state.setQtdeAnimais(animalQuantity);
	}

	public Date getInconvenienceDate() {
		return state.getDataIncomodo();
	}

	public void setInconvenienceDate(Date inconvenienceDate) {
		state.setDataIncomodo(inconvenienceDate);
	}

	public Address getOccurenceLocalAddress() {
		return state.getEnderecoLocalOcorrencia();
	}

	public void setOccurenceLocalAddress(Address occurenceLocalAddress) {
		state.setEnderecoLocalOcorrencia(occurenceLocalAddress);
	}

}
