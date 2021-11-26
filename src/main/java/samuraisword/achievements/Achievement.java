package samuraisword.achievements;



import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends BaseEntity {
	
	
	
	
	@NotEmpty
	private String title;
	
	
	@NotEmpty
	private String body;


	@ManyToOne
	@JoinColumn(name = "typeL")
	private AchievementType type;
	
	public AchievementType getType() {
		return this.type;
	}

	public void AchievementType(AchievementType type) {
		this.type = type;
	}
	
	@ManyToOne
	@JoinColumn(name = "typeR")
	private RolType types;
	
	public RolType getTypes() {
		return this.types;
	}

	public void Rol(RolType types) {
		this.types = types;
	}
	

	
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	

	
}