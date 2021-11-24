package samuraisword.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.ehcache.spi.service.OptionalServiceDependencies;

import lombok.Getter;
import lombok.Setter;
import samuraisword.player.Player;
import samuraisword.samples.petclinic.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity{
	

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
	private List<Player> listPlayers;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	

	public Game() {
		this.listPlayers=new ArrayList<>();
	}
	
	public Integer getNumPlayers() {
		return this.listPlayers.size();
	}
}
