package samuraisword.character;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToMany;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import samuraisword.cardhand.CardHand;
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
	
	@OneToOne(mappedBy = "character")
    private Player player;


}
