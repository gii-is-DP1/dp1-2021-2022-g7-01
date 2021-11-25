package samuraisword.samples.petclinic.card;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "cards")
public class Card extends BaseEntity{

	String name;
	
	String image;
	
	Integer cardsPerDeck;
	

	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	
}
