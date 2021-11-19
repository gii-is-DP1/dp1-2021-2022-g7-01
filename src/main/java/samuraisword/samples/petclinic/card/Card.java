package samuraisword.samples.petclinic.card;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import samuraisword.samples.petclinic.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{

	String name;
	
	String image;
	
	Integer cardsPerDeck;
	
}
