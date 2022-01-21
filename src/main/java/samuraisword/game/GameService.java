package samuraisword.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.cardhand.CardHand;
import samuraisword.character.Character;
import samuraisword.character.CharacterService;
import samuraisword.comment.Comment;
import samuraisword.player.Player;
import samuraisword.player.Rol;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.card.RedCard;

@Service
public class GameService {

	private GameRepository gameRepository;
	private CharacterService characterService;

	private final Integer MAX_CARDS_HAND = 7;
	private final Integer NUM_CARD_DRAWN = 2;

	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public Collection<Game> findAll() {
		return gameRepository.findAll();
	}

	public Optional<Game> findById(int idGame) {
		return gameRepository.findById(idGame);
	}

	@Transactional
	public void saveGame(Game game) throws DataAccessException {
		// creating game
		gameRepository.save(game);
	}

	public void deleteGame(int idGame) {
		gameRepository.deleteById(idGame);
	}

	public List<Card> createDeck(CardService cardService) {
		// Crear y guardar deck
		List<Card> gameDeck = new ArrayList<>();// funcion que crea el deck predeterminado y ordenado aleatoriamente
		for (Card card : cardService.findAll()) {
			for (int i = 0; i < card.getCardsPerDeck(); i++) {
				String name = card.getName();
				switch (cardService.findColor(name).get()) {
				case ("Red"):
					Integer rango = Integer.valueOf(cardService.findRange(name).get());
					Integer damage = Integer.valueOf(cardService.findDamage(name).get());
					RedCard redCard = RedCard.of(name, card.getImage(), rango, damage, "Red");
					gameDeck.add(redCard);
					break;
				case ("Yellow"):
					Card yellowCard = Card.of(name, card.getImage(), "Yellow");
					gameDeck.add(yellowCard);
					break;
				case ("Blue"):
					Card blueCard = Card.of(card.getName(), card.getImage(), "Blue");
					gameDeck.add(blueCard);
					break;
				}
			}
		}
		Collections.shuffle(gameDeck);

		return gameDeck;
	}

	public List<Player> asignCharacterAndHearts(List<Player> players, List<Character> characters) {

		for (Player p : players) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, characters.size());
			p.setCharacter(characters.get(randomNum));

			characters.remove(randomNum);

			p.setMaxHearts(p.getCharacter().getLife());
			p.setCurrentHearts(p.getCharacter().getLife());
			p.setDamageBonus(0);
			p.setDistanceBonus(0);
			p.setWeaponBonus(0);
		}
		return players;
	}

	public List<Player> asignRolAndHonor(List<Player> listPlayers) {

		Collections.shuffle(listPlayers);
		// Roles asignados segun reglas del juego
		switch (listPlayers.size()) {
		case 4:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(1).setRol(Rol.SAMURAI);
			listPlayers.get(2).setRol(Rol.NINJA);
			listPlayers.get(3).setRol(Rol.NINJA);
			// setHonor
			for (Player p : listPlayers) {
				if (p.getRol().equals(Rol.SHOGUN))
					p.setHonor(5);
				else
					p.setHonor(3);
			}
			break;
		case 5:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(1).setRol(Rol.SAMURAI);
			listPlayers.get(2).setRol(Rol.NINJA);
			listPlayers.get(3).setRol(Rol.NINJA);
			listPlayers.get(4).setRol(Rol.RONIN);

			for (Player p : listPlayers) {
				if (p.getRol().equals(Rol.SHOGUN))
					p.setHonor(5);
				else
					p.setHonor(3);
			}
			break;
		case 6:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(1).setRol(Rol.SAMURAI);
			listPlayers.get(2).setRol(Rol.NINJA);
			listPlayers.get(3).setRol(Rol.NINJA);
			listPlayers.get(4).setRol(Rol.NINJA);
			listPlayers.get(5).setRol(Rol.RONIN);

			for (Player p : listPlayers) {
				if (p.getRol().equals(Rol.SHOGUN))
					p.setHonor(5);
				else
					p.setHonor(4);
			}
			break;
		case 7:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(1).setRol(Rol.SAMURAI);
			listPlayers.get(2).setRol(Rol.NINJA);
			listPlayers.get(3).setRol(Rol.NINJA);
			listPlayers.get(4).setRol(Rol.NINJA);
			listPlayers.get(5).setRol(Rol.RONIN);
			listPlayers.get(6).setRol(Rol.SAMURAI);

			for (Player p : listPlayers) {
				if (p.getRol().equals(Rol.SHOGUN))
					p.setHonor(5);
				else
					p.setHonor(4);
			}
			break;
		default:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(0).setHonor(5);
		}

		return listPlayers;
	}

	public List<Player> asignOrder(List<Player> listPlayers) {
		// De el ultimo tratamiento a listPlayers (asignRolAndHonor) el SHOGUN será
		// siempre el indice 0.
		// queremos hacer un ultimo shuffle a listPlayers para que no sea siempre el
		// mismo orden de roles
		// definidos en asignRolandHonor, pero queremos que SHOGUN siga ocupando el
		// indice 0 ya que es importante
		// para el reparto de cartas ya que en funcion de los jugadores que hayan entre
		// un jugador y el SHOGUN se le
		// reparten mas o menos cartas.

		Player shogun = listPlayers.get(0);
		listPlayers.remove(0);
		Collections.shuffle(listPlayers);
		listPlayers.add(0, shogun);

		return listPlayers;
	}

	public void asignCards(List<Card> gameDeck, List<Player> players) {
		int cardsGiven = 4;
		for (int i = 0; i < players.size(); i++) { // player tiene 2 cardhands la 0 indica la mano, la 1 las equipadas
			// Normas del reparto de cartas:
			// En la lista players el indice 0 corresponde al shogun ya que esta funcion es
			// inmediatamente posterior a asignOrder.
			/*
			 * 0º Shogun: 4 cards 1st and 2nd player: 5 cards; 3rd and 4th player (if
			 * present): 6 cards 5th and 6th player (if present): 7 cards
			 * 
			 * Al shogun con indice 0 se le repartiran 4 cartas, y cada vez que el indice
			 * coincida con ser impar, el n cartas a repartir aumenta en 1.
			 */
			Player player = players.get(i);
			if (i % 2 == 1) {
				cardsGiven = cardsGiven + 1;
			}

			player.setHand(new ArrayList<>());
			player.setEquipment(new ArrayList<Card>());
			for (int e = 0; e < cardsGiven; e++) {
				player.getHand().add(gameDeck.get(e));
				gameDeck.remove(e);
			}
		}
	}

	public List<Player> playersInRangeOfAttack(Game game, RedCard attackWeapon, Player attacker) {
		List<Player> playerList = game.getListPlayers();
		List<Player> inRange = new ArrayList<>();
		// Omitimos los jugadores inofensivos (disabled) para el calculo del rango
		playerList.stream().filter(x -> x.isDisabled()).forEach(y -> playerList.remove(y));
		// calculamos la distancia minima desde cada jugador al atacante
		for (Player p : playerList) {
			if(p.getCharacter().getName().equals("Chiyome"));
			Integer distancia1 = calcDistance(attacker, p, playerList);
			Integer distancia2 = calcDistance(p, attacker, playerList);
			Integer distMin = List.of(distancia1, distancia2).stream().min(Comparator.naturalOrder()).get();
			if (distMin <= attackWeapon.getRange() || attacker.getCharacter().getName().equals("Kojiro")) {
				inRange.add(p);
			}
		}
		return inRange;
	}

	private Integer calcDistance(Player p1, Player p2, List<Player> playerList) {
		Integer playersBetween = playerList.indexOf(p1) - playerList.indexOf(p2);
		if (playersBetween < 0)
			playersBetween += playerList.size();
		return playersBetween;
	}

	public Boolean endTurn(Game game) {
		Boolean correctMaxCardHand = game.getCurrentPlayer().getHand().size() <= MAX_CARDS_HAND;
		
		if (correctMaxCardHand) {
			Integer numPlayers = game.getListPlayers().size();
			Integer nextPlayerIndex = (game.getListPlayers().indexOf(game.getCurrentPlayer()) + 1) % numPlayers;
			game.setCurrentPlayer(game.getListPlayers().get(nextPlayerIndex));
			game.setGamePhase(GamePhase.RECOVERY);
		} else {
			game.setGamePhase(GamePhase.DISCARD);
		}
		return correctMaxCardHand;
	}

	public void processRecoveryPhase(Game game) {
		Player player = game.getCurrentPlayer();
		if (player.isDisabled() && player.getCurrentHearts() <= 0) {
			player.setCurrentHearts(player.getCharacter().getLife());
			player.setDisabled(false);
		}
		game.setGamePhase(GamePhase.DRAW);
	}

	public void processDrawPhase(Game game) {
		Player player = game.getCurrentPlayer();
		for (int i = 0; i < NUM_CARD_DRAWN; i++) {
			Card card = game.getDeck().get(0);
			player.getHand().add(card);
			game.getDeck().remove(0);
		}
		game.setGamePhase(GamePhase.MAIN);
	}

	public void substractHearts(Player objective, RedCard attackWeapon) {
		objective.setCurrentHearts(objective.getCurrentHearts() - attackWeapon.getDamage());
		if (objective.getCurrentHearts() <= 0) {
			objective.setHonor(objective.getHonor() - 1);
			objective.setDisabled(true);
			objective.setCurrentHearts(0);
		}

	}

	public Player findPlayerInGameByName(Game game, String objectiveName) {
		return game.getListPlayers().stream().filter(x -> x.getUser().getUsername().equals(objectiveName)).findFirst()
				.get();
	}
	public void handleAttack(Game game, Player attacker, Player objective, RedCard attackWeapon) {
		if( !objective.getHand().stream().anyMatch(x-> x.getName().equals("parada")) ) {
			substractHearts(objective, attackWeapon);
		}
	}

	public boolean checkAllPlayersHavePositiveHonor(Game game) {
		return game.getListPlayers().stream().allMatch(x-> x.getHonor() > 0);
	}


	public Rol calcWinners(Game game) {
		Double bonusNinja = 1.5;
		Double bonusSamurai = 0.;
		Double bonusRonin = 0.;
		switch (game.getListPlayers().size()) {
		case 4:
			bonusSamurai = 2.;
			break;
		case 5:
			bonusSamurai = 1.;
			bonusRonin = 2.;
			break;
		case 6:
			bonusSamurai = 2.;
			bonusRonin = 3.;
			break;
		case 7:
			bonusSamurai = 1.;
			bonusRonin = 3.;
			break;
		}
		
		Map<Rol, Double> pointsPerRole = calcPointsPerRole(game);
		//Sumamos puntos por equipos aplicando bonus
	    pointsPerRole.put(Rol.SAMURAI, pointsPerRole.get(Rol.SHOGUN) + pointsPerRole.get(Rol.SAMURAI)*bonusSamurai);
		pointsPerRole.put(Rol.NINJA, pointsPerRole.get(Rol.NINJA)*bonusNinja);
		pointsPerRole.put(Rol.RONIN, pointsPerRole.get(Rol.RONIN)*bonusRonin);
		
		Entry<Rol, Double> winnerRol = pointsPerRole.entrySet().stream().max(Map.Entry.comparingByValue()).get();
		
		return winnerRol.getKey();
	}

	private Map<Rol, Double> calcPointsPerRole(Game game) {
		return game.getListPlayers().stream().collect(Collectors.groupingBy(Player::getRol, Collectors.summingDouble(x->x.getHonor())));
	}
	
}
