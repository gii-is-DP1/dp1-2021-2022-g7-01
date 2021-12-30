package samuraisword.invitations;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import samuraisword.game.Game;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Table
@Entity
public class Invitation extends BaseEntity{
	
	
	@ManyToOne
	@JoinColumn(name="user_sender")
	private User userSender;

	@ManyToOne
	@JoinColumn(name="user_addresse")
	private User userAddresse;
	
	
	@OneToOne
	private Game game;
	
	
	
	
	
	
	
	
	
	
}
