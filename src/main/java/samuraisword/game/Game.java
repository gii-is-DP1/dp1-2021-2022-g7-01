package samuraisword.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ehcache.spi.service.OptionalServiceDependencies;

import lombok.Getter;
import lombok.Setter;
import samuraisword.cardhand.CardHand;
import samuraisword.invitations.Invitation;
import samuraisword.player.Player;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity{
	

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
	private List<Player> listPlayers;
	
	
	@ManyToOne
	private User creator;

	@Column
	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> wonPlayers;

	
	@Transient
	private List<Card> deck;
	
	@Transient
	private List<Card> discardPile;
	
	
	private GamePhase gamePhase;
	
	@Transient
	private Player currentPlayer;
	
	@Transient
	private GameStatus status;
	
	private Date startDate;
	
	private Date endDate;
	
	@Transient
	private List<Player> playersInRange;
	
	@Transient
	private Card useCard;
	
	@Transient
	private Player attackerPlayer;
	
	@Transient
	private Integer attackerDamage;
	
	@Transient
	private List<Player> waitingForPlayer;
	
	@Transient
	private List<Card> listJiuJitsu;
	
	@Transient
	private Player playerChoose;
	
	@Transient
	private String error;
			
	public Game() {
		this.listPlayers = new ArrayList<>();
		this.wonPlayers = new ArrayList<>();
	}
	
	public Integer getNumPlayers() {
		return this.listPlayers.size();
	}
	
	
	
	
	
	
	
	
}
