package samuraisword.cardhand;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import samuraisword.player.Player;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.RedCard;
import samuraisword.samples.petclinic.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "card_hands")
public class CardHand extends BaseEntity {

	@ManyToMany
	@JoinTable(
	  name = "cards_in_hands", 
	  joinColumns = @JoinColumn(name = "card_hand_id"), 
	  inverseJoinColumns = @JoinColumn(name = "card_id"))
	List<Card> cardList;
	
	@ManyToOne
	@JoinColumn(name="player")
	private Player player;


	public static CardHand createDeck() {
		CardHand res = new CardHand();
		return res;
	}

	public static CardHand Empty() {
		return new CardHand();
	}

	
	
	
}
