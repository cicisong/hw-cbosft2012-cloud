package healthwatcher.model.complaint;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Situation implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String description;

	public static int QUEIXA_ABERTA = 1;

	public static int QUEIXA_SUSPENSA = 2;

	public static int QUEIXA_FECHADA = 3;

	public Situation(Long codigo, String descricao) {
		this.id = codigo;
		this.description = descricao;
	}

	public Long getCode() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
