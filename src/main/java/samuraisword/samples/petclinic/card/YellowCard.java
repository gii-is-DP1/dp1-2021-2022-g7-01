package samuraisword.samples.petclinic.card;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Yellow")
public class YellowCard extends Card{
	private String description;
}
