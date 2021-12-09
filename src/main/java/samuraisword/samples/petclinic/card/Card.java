package samuraisword.samples.petclinic.card;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="card_color", discriminatorType = DiscriminatorType.STRING)
@Table(name = "cards")
public class Card extends BaseEntity{

	private String name;
	
	private String image;
	
	private Integer cardsPerDeck;

	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	
	@ManyToMany
	@JoinTable(
	  name = "cards_in_hands", 
	  joinColumns = @JoinColumn(name = "card_id"), 
	  inverseJoinColumns = @JoinColumn(name = "card_hand_id"))
	List<Card> cardListOf;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
