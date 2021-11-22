package samuraisword.player;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;
import samuraisword.game.Game;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "players")

public class Player extends BaseEntity{
	
	
	
	
	private Rol rol;
	
	@ManyToOne
	private Game game;
	
	
	private Integer maxHearts;
	
	
	private Integer currentHearts;
	
	
	private Integer honor;
		
	
	private Integer position;
	
	@OneToOne
	@JoinColumn(name="username")
	private User user;
	
	

	@Override
	public String toString() {
		return ""+user.getUsername();
	}
	
	
}
