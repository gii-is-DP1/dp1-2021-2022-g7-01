package samuraisword.player;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "players")

public class Player extends BaseEntity{
	
	@NotEmpty
	private String name;
	
	private String photo;
	
	@NotEmpty
	private Rol rol;
	
	@NotEmpty
	private Integer maxHearts;
	
	@NotEmpty
	private Integer currentHearts;
	
	@NotEmpty
	private Integer honor;
		
	@NotEmpty
	private Integer position;
	
	@OneToOne
	@JoinColumn(name="username")
	private User user;
}
