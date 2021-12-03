package samuraisword.player;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import samuraisword.achievements.RolType;
import samuraisword.cardhand.CardHand;
import samuraisword.game.Game;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{
	
	@ManyToOne
	private Game game;
	
	private Integer maxHearts;
	
	private Integer currentHearts;
	
	private Integer honor;
	
	private Integer position;
	
	private Boolean wonGame;
	
	private Rol rol;
	
	@OneToOne
	@JoinColumn(name="username")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "card_hand_id")
	private CardHand cardHand;
	
	@Override
	public String toString() {
		return "" + user.getUsername();
	}
	
}
