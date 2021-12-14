package samuraisword.samples.petclinic.card;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Blue")
public class BlueCard extends Card{
	private String description;
}