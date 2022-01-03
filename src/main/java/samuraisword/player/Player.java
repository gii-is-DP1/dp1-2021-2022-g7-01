package samuraisword.player;

import java.util.List;
import samuraisword.character.Character;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.Getter;
import lombok.Setter;
import samuraisword.cardhand.CardHand;
import samuraisword.game.Game;
import samuraisword.samples.petclinic.card.Card;
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
	
	private Boolean wonGame;
	
	private Rol rol;
	
	private boolean esInofensivo; 
	
	private Integer distanceBonus=0;
	private Integer weaponBonus=0;
	private Integer damageBonus=0;
	
	@ManyToOne
	@JoinColumn(name="username")
	private User user;

	@Transient
	private List<Card> hand;
	
	@Transient
	private List<Card> equipment;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "character")
    private Character character;
	
	@Override
	public String toString() {
		return "" + user.getUsername();
	}
	
}
