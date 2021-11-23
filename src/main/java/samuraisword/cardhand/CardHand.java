package samuraisword.cardhand;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import samuraisword.player.Player;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "card_hands")
public class CardHand extends BaseEntity {
	
	@ManyToMany
	@JoinTable(
	  name = "card_in_hands", 
	  joinColumns = @JoinColumn(name = "card_hand_id"), 
	  inverseJoinColumns = @JoinColumn(name = "card_id"))
	List<Card> cardList;
	
	@OneToOne(mappedBy = "cardHand")
	private Player player;
	
	
}
