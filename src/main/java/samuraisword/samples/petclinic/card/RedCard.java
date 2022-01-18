package samuraisword.samples.petclinic.card;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import samuraisword.cardhand.CardHand;

@Getter
@Setter
@Entity
@DiscriminatorValue("Red")
public class RedCard extends Card {
	
	private Integer range;
	private Integer damage;
	
	public static RedCard of(String name, String image, Integer range, Integer damage, String color) {
		RedCard card = new RedCard();
		card.setName(name);
		card.setImage(image);
		card.setRange(range);
		card.setDamage(damage);
		card.setColor(color);
		return card;
	}
	
}


