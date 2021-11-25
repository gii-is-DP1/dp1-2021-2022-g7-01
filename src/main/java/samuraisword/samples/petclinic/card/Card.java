package samuraisword.samples.petclinic.card;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToMany;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import samuraisword.cardhand.CardHand;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{

	private String name;
	
	private String image;
	
	private Integer cardsPerDeck;

	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	
	@ManyToMany(mappedBy = "cardList")
	private List<CardHand> cardHand;

}
