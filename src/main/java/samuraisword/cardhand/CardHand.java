package samuraisword.cardhand;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "cardhands")
public class CardHand extends BaseEntity {
	
	
//Hay que comentarlo porque la asociacion con Cards no funciona	
//	@NotEmpty
//	private List<Card> cards;
	
}
