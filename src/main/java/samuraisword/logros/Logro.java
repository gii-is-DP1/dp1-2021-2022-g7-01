package samuraisword.logros;



import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "logros")
public class Logro extends BaseEntity {
	
	
	
	
	@NotEmpty
	private String title;
	
	
	@NotEmpty
	private String body;

	
	@NotEmpty
	private String categoria;

	
	@NotEmpty
	private String rol;
	
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	

	
}