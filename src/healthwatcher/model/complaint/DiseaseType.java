package healthwatcher.model.complaint;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DiseaseType implements java.io.Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String description;

	@Persistent
	private String manifestation;

	@Persistent
	private String duration;
	
	@Persistent
	private int teste; //codigo pra linkar a classe symptom.

	private List symptoms;
	
	public DiseaseType() {
		symptoms = new ArrayList();
	}

	public DiseaseType(String name, String description, String manifestation, String duration,
			List symptoms) {

		this.name = name;
		this.description = description;
		this.manifestation = manifestation;
		this.duration = duration;
		this.symptoms = symptoms;
	}

	public void delete() {
	}
	
	public void setCode(int codigo){	
		this.teste = codigo;
	}
	
	public int getCode(){
		return this.teste;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descricao) {
		this.description = descricao;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duracao) {
		this.duration = duracao;
	}

	public String getManifestation() {
		return manifestation;
	}

	public void setManifestation(String manifestacao) {
		this.manifestation = manifestacao;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public List getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List sintomas) {
		this.symptoms = sintomas;
	}

}
