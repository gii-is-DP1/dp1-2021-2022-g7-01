package samuraisword.game;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import samuraisword.character.Character;
import samuraisword.cardhand.CardHand;
import samuraisword.cardhand.CardHandService;
import samuraisword.character.CharacterService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.player.Rol;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.card.RedCard;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;


@Controller
public class GameController {
	
	private final GameService gameService;
	private final PlayerService playerService;
	private final UserService userService;
	private final CardHandService cardHandService;
	private final CardService cardService;
	private final CharacterService characterService;
 	
	private static final String VIEWS_CREATE_GAME = "game/createGame";
	
	@Autowired
	public GameController(GameService GameService, UserService userService, PlayerService playerService, CardHandService cardHandService,
				CardService cardService, CharacterService characterService) {
		this.gameService = GameService;
		this.userService = userService;
		this.playerService = playerService;
		this.cardHandService = cardHandService;
		this.cardService = cardService;
		this.characterService = characterService;
	}
	
	@GetMapping(value = "/game/new")
	public String initCreationForm(Map<String, Object> model, HttpServletResponse a) {
		Game game = new Game();
		a.addHeader("Refresh", "1");
		model.put("game", game);
		return VIEWS_CREATE_GAME;
	}
	
	@PostMapping(value = "/game/new")
	public String processCreationForm() {
		
			//creating owner, user and authorities
			Game game= new Game();
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Player player = new Player();
			player.setUser(user);
			player.setGame(game);
			this.gameService.saveGame(game);
			this.userService.saveUser(user);
			this.playerService.savePlayer(player);
			return "redirect:/game/" + game.getId();
	}
	
	@GetMapping("/game/{gameId}")
	public String showGame(@PathVariable("gameId") int gameId, Map<String, Object> model, HttpServletResponse a) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game=gameService.findById(gameId).get();
		a.addHeader("Refresh", "1");
		model.put("now", new Date());	
		model.put("listFriends", user.getListAllFriends());
		model.put("user", user.getUsername());
		model.put("listPlayer", game.getListPlayers());
		model.put("gameId", gameId);
		return "game/game";
	}

	@GetMapping(value = { "/game/delete/{id_game}" })
	public String deleteCommentForm(@PathVariable("id_game") int gameId, Map<String, Object> model) {
		gameService.deleteGame(gameId);
		return "redirect:/game/new";
	}
	
	@GetMapping(value = {"/game/start/{id_game}"})
	public String initGame(@PathVariable("id_game") int gameId,  Map<String, Object> model) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = gameService.findById(gameId).get();
		
		CardHand gameDeck = createDeck();
		CardHand discardPile = CardHand.empty();
		
		asignCharacterAndHearts(game.getListPlayers());
		
		asignRolAndHonor(game.getListPlayers());
		
		asignOrder(game.getListPlayers());
		
		//asign cards to players
		
		model.put("user", user.getUsername());
		model.put("listPlayer", game.getListPlayers());
		model.put("gameId", gameId);
		model.put("gameDeck", gameDeck);
		model.put("discardPile", discardPile);
		
		return "/game/gameboard";
	}
	
	private void asignOrder(List<Player> listPlayers) {
		// De el ultimo tratamiento a listPlayers (asignRolAndHonor) el 1o SHOGUN será siempre el indice 0.
		// queremos hacer un ultimo shuffle a listPlayers para que no sea siempre el mismo orden de roles
		//definidos en asignRolandHonor, pero queremos que SHOGUN siga ocupando el indice 0 ya que es importante 
		//para el reparto de cartas ya que en funcion de los jugadores que hayan entre un jugador y el SHOGUN se le
		//reparten mas o menos cartas.
		
		Player shogun = listPlayers.get(0);
		listPlayers.remove(0);
		Collections.shuffle(listPlayers);
		listPlayers.add(0, shogun);
		
	}

	private void asignRolAndHonor(List<Player> listPlayers) {
		
		Collections.shuffle(listPlayers);
		
		switch(listPlayers.size()) {
		case 4:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(1).setRol(Rol.SAMURAI);
			listPlayers.get(2).setRol(Rol.NINJA);
			listPlayers.get(3).setRol(Rol.NINJA);
			//setHonor
			for(Player p : listPlayers) {
				if(p.getRol().equals(Rol.SHOGUN)) p.setHonor(5);
				else p.setHonor(3);
			}
		case 5:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(1).setRol(Rol.SAMURAI);
			listPlayers.get(2).setRol(Rol.NINJA);
			listPlayers.get(3).setRol(Rol.NINJA);
			listPlayers.get(4).setRol(Rol.RONIN);
			
			for(Player p : listPlayers) {
				if(p.getRol().equals(Rol.SHOGUN)) p.setHonor(5);
				else p.setHonor(3);
			}
		case 6:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(1).setRol(Rol.SAMURAI);
			listPlayers.get(2).setRol(Rol.NINJA);
			listPlayers.get(3).setRol(Rol.NINJA);
			listPlayers.get(4).setRol(Rol.NINJA);
			listPlayers.get(5).setRol(Rol.RONIN);
			
			for(Player p : listPlayers) {
				if(p.getRol().equals(Rol.SHOGUN)) p.setHonor(5);
				else p.setHonor(4);
			}
		case 7:
			listPlayers.get(0).setRol(Rol.SHOGUN);
			listPlayers.get(1).setRol(Rol.SAMURAI);
			listPlayers.get(2).setRol(Rol.NINJA);
			listPlayers.get(3).setRol(Rol.NINJA);
			listPlayers.get(4).setRol(Rol.NINJA);
			listPlayers.get(5).setRol(Rol.RONIN);
			listPlayers.get(6).setRol(Rol.SAMURAI);
			
			for(Player p : listPlayers) {
				if(p.getRol().equals(Rol.SHOGUN)) p.setHonor(5);
				else p.setHonor(4);
			}
		}
	}

	private void asignCharacterAndHearts(List<Player> players) {
		List<Character> characters = (List<Character>) characterService.findAll();
		
		for(Player p : players) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, characters.size() + 1);
			p.setCharacter(characters.get(randomNum));
			
			characters.remove(randomNum);
			
			p.setMaxHearts(p.getCharacter().getLife());
			p.setCurrentHearts(p.getCharacter().getLife());
		}
	}

	private CardHand createDeck() {
		//Crear y guardar deck
				CardHand gameDeck = new CardHand();//funcion que crea el deck predeterminado y ordenado aleatoriamente		
				gameDeck.setCardList(new ArrayList<>());
				for(Card card : cardService.findAll()) {
					for(int i = 0; i < card.getCardsPerDeck(); i++) {
						String name = card.getName();
						switch(cardService.findColor(name).get()) {
						case("Red"):
							Integer rango= Integer.valueOf(cardService.findRange(name).get());
							Integer damage= Integer.valueOf(cardService.findRange(name).get());
							RedCard redCard = RedCard.of(name, card.getImage(), rango, damage);
							gameDeck.getCardList().add(redCard);
						case("Yellow"):
							Card yellowCard = Card.of(name, card.getImage());
							gameDeck.getCardList().add(yellowCard);
						case("Blue"):
							Card blueCard = Card.of(card.getName(), card.getImage());
							gameDeck.getCardList().add(blueCard);
						}	
					}
				}
				Collections.shuffle(gameDeck.getCardList());
				
				return gameDeck;
	}
	
	
	
	

}
