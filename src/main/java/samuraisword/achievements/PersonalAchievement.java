package samuraisword.achievements;



import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "personalAchievements")
public class PersonalAchievement extends BaseEntity {
	
	
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="title")
	private Achievement achievement;
	
}