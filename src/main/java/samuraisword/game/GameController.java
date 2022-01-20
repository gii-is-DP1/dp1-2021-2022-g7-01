package samuraisword.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import samuraisword.character.Character;
import samuraisword.character.CharacterService;
import samuraisword.invitations.Invitation;
import samuraisword.invitations.InvitationService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
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
	private final CardService cardService;
	private final InvitationService invitationService;
	private final CharacterService characterService;

	private static final String VIEWS_CREATE_GAME = "game/createGame";

	@Autowired

	public GameController(GameService GameService, UserService userService, PlayerService playerService,
			CardService cardService, CharacterService characterService, InvitationService invitationService) {
		this.gameService = GameService;
		this.userService = userService;
		this.invitationService = invitationService;
		this.playerService = playerService;
		this.cardService = cardService;
		this.characterService = characterService;
	}

	@GetMapping(value = "/game/new")
	public String initCreationForm(Map<String, Object> model, HttpServletResponse a) {
		Game game = new Game();
		a.addHeader("Refresh", "1");

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Invitation> li = (List<Invitation>) invitationService.findAllByUser(userDetails.getUsername());

		model.put("game", game);
		model.put("listInvitations", li);
		return VIEWS_CREATE_GAME;
	}

	@PostMapping(value = "/game/new")
	public String processCreationForm() {

		// creating owner, user and authorities
		Game game = new Game();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Player player = new Player();
		player.setUser(user);
		player.setGame(game);
		game.setListPlayers(List.of(player));
		gameService.saveGame(game);
		userService.saveUser(user);
		playerService.savePlayer(player);
		return "redirect:/game/" + game.getId();
	}

	@GetMapping("/game/{gameId}")
	public String showGame(@PathVariable("gameId") int gameId, Map<String, Object> model, HttpServletResponse a) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = gameService.findById(gameId).get();
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

	@GetMapping(value = { "/game/start/{id_game}" })
	public String initGame(@PathVariable("id_game") int gameId, Map<String, Object> model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = gameService.findById(gameId).get();

		game.setGamePhase(GamePhase.MAIN);
		game.setDeck(gameService.createDeck(cardService));
		game.setDiscardPile(new ArrayList<>());

		List<Character> characters = (List<Character>) characterService.findAll();

		List<Player> players = game.getListPlayers();

		// players de prueba
		Player p1 = playerService.findById(1).get();

		Player p2 = playerService.findById(2).get();

		Player p3 = playerService.findById(3).get();

		Player p4 = playerService.findById(4).get();
//	
//		Player p5 = playerService.findById(5).get();
//		
//		Player p6 = playerService.findById(6).get();

    	players.add(p1);
    	players.add(p2);
		players.add(p3);
		players.add(p4);
//		players.add(p5);
//		players.add(p6);
		// players de prueba

		gameService.asignCharacterAndHearts(players, characters);

		gameService.asignRolAndHonor(players);

		gameService.asignOrder(players);

		game.setListPlayers(players);
		game.setCurrentPlayer(players.get(0));

		for (Player player : game.getListPlayers()) {
			playerService.savePlayer(player);
		}

		gameService.asignCards(game.getDeck(), players);

		GameSingleton.getInstance().getMapGames().put(game.getId(), game);

		model.put("POVplayer", user);
		model.put("game", game);

		return "/game/gameboard";
	}

	@PostMapping(value = { "/game/attack/selectplayer" })
	public String handleAttack(@RequestParam("gameId") Integer gameId, @RequestParam("cardName") String cardName,
			Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		game.setGamePhase(GamePhase.ATTACK);
		RedCard attackWeapon = cardService.findRedCardByName(cardName).get();

		Player attacker = game.getCurrentPlayer();
		
		List<Player> inRange = gameService.playersInRangeOfAttack(game, attackWeapon, attacker);
		
		model.put("attackWeapon", attackWeapon);
		model.put("inRange", inRange);
		model.put("game", game);
		model.put("POVplayer", user);

		return "/game/gameboard";
	}

	@PostMapping(value = { "/game/attack/playerselected" })
	public String performAttack(@RequestParam("gameId") Integer gameId,
			@RequestParam("objectivePlayer") String objectiveName, @RequestParam("cardName") String cardName,
			Map<String, Object> model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		RedCard attackWeapon = cardService.findRedCardByName(cardName).get();

		Player objective = gameService.findPlayerInGameByName(game, objectiveName); //
		Player attacker = game.getCurrentPlayer();
		
		//Falta hacer la parada por aqui
		
		int n = objective.getCurrentHearts();
		//quitamos vida

		if(objective.getCurrentHearts()==n) objective.setCurrentHearts(objective.getCurrentHearts() -1);
		objective.setCurrentHearts(objective.getCurrentHearts()-attackWeapon.getDamage());
		//descartamos carta
		attacker.getHand().removeIf(x-> x.equals(attackWeapon));

		gameService.substractHearts(objective, attackWeapon);
		
		//descartamos la 1era carta que coincida con el nombre
		cardService.removeCardByName(cardName, game.getCurrentPlayer().getHand());

		
		Boolean hasAdvancedPhase = gameService.endTurn(game);
		if(hasAdvancedPhase) {
			gameService.processRecoveryPhase(game);
			gameService.processDrawPhase(game);
		}
		model.put("game", game);
		model.put("POVplayer", user);
		return "/game/gameboard";
	}

	@PostMapping(value = { "/game/end-turn" })
	public String endTurn(@RequestParam("gameId") Integer gameId, Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		Boolean hasAdvancedPhase = gameService.endTurn(game);
		if(hasAdvancedPhase) {
			gameService.processRecoveryPhase(game);
			gameService.processDrawPhase(game);
		}
		model.put("game", game);
		model.put("POVplayer", user);
		return "/game/gameboard";
	}
	
	@PostMapping(value = { "/game/discard-card" })
	public String discardCard(@RequestParam("gameId") Integer gameId, @RequestParam("cardName") String cardName, Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		cardService.removeCardByName(cardName, game.getCurrentPlayer().getHand());
		Boolean hasAdvancedPhase = gameService.endTurn(game);
		if(hasAdvancedPhase) {
			gameService.processRecoveryPhase(game);
			gameService.processDrawPhase(game);
		}
		model.put("game", game);
		model.put("POVplayer", user);
		return "/game/gameboard";
	}

	@PostMapping(value = { "/game/select" })
	public String acceptController(@RequestParam("gameId") Integer gameId, @RequestParam("cardName") String cardName,
			Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Optional<Card> card = cardService.findByName(cardName);
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		Player p = game.getCurrentPlayer();

		if (card.get().getName().contains("armadura")) {
			gameService.statUp(p, "distanceBonus", 1);
		} else if (card.get().getName().contains("concentracion")) {
			gameService.statUp(p, "damageBonus", 1);
		} else if (card.get().getName().contains("desenvainado")) {
			gameService.statUp(p, "weaponBonus", 1);
		}
		p.getEquipment().add(card.get());
		cardService.removeCardByName(cardName, p.getHand());		
		model.put("game", game);
		model.put("POVplayer", user);
		return "/game/gameboard";
	}
	
	@PostMapping(value = { "/game/steal" })
	public String stealCard(@RequestParam("gameId") Integer gameId,
			Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		Player p = game.getCurrentPlayer();		
		
		Random r = new Random();
		int valorDado = r.nextInt(game.getDeck().size());
		
		p.getHand().add(game.getDeck().get(valorDado));
		game.getDeck().remove(valorDado);
				
		model.put("game", game);
		model.put("POVplayer", user);
		return "/game/gameboard";
	}
	
	@PostMapping(value = { "/game/stealPlayer" })
	public String stealCardPlayer(@RequestParam("gameId") Integer gameId, @RequestParam("playerName") String playerName,
			Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		Player p = game.getCurrentPlayer();	
		Player p2 = gameService.findPlayerInGameByName(game, playerName);
		
		
		Random r = new Random();
		int valorDado = r.nextInt(p2.getHand().size());
		
	//	System.out.println(p2.getHand().size()+"////////////////////////////////////////////////////////////");
		
		p.getHand().add(game.getDeck().get(valorDado));
		p2.getHand().remove(valorDado);
				
		model.put("game", game);
		model.put("POVplayer", user);
		return "/game/gameboard";
	}

}
