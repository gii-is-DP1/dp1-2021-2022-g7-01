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
import samuraisword.game.GameStatus;
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
	

	
	public void action(Player p) {
		
		if(p.getCharacter().getName().equals("Benkei")) {
			p.setDistanceBonus(p.getDistanceBonus()+1);
		}else if(p.getCharacter().getName().equals("Goemon")) {
			p.setWeaponBonus(p.getWeaponBonus()+1);
		}else if(p.getCharacter().getName().equals("Hideyoshi")) {
			p.setAntiDamageBonus(p.getAntiDamageBonus()+1);
		}else if(p.getCharacter().getName().equals("Musashi")) {
			p.setDamageBonus(p.getDamageBonus()+1);
		}
		
		
	}
	
	
	
}
