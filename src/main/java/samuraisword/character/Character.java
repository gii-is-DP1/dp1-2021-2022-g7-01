package samuraisword.character;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import samuraisword.cardhand.CardHand;
import samuraisword.game.GamePhase;
import samuraisword.player.Player;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "characters")
public class Character extends BaseEntity{
	
	private String name;
	
	private Integer life;
	
	private String image;
	
	private String text;
	
	private GamePhase gamePhase;
	
	
}
