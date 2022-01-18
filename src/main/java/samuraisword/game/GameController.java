package samuraisword.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

	public GameController(GameService GameService, UserService userService, PlayerService playerService, CardService cardService, CharacterService characterService,
			InvitationService invitationService) {
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
	public String handleAttack(@RequestParam("gameId") Integer gameId, @RequestParam("cardName") String cardName, Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		game.setGamePhase(GamePhase.ATTACK);
		RedCard attackWeapon = cardService.findRedCardByName(cardName).get();
		
		List<Player> inRange = new ArrayList<>();
		
		for(Player p : game.getListPlayers()) {
			//calcular distancia--> Integer distance = gameService.calcDistance(Player p1, Player p2, List<Player> game.getListPlayer())
			if(1 <= attackWeapon.getRange()) {
				inRange.add(p);
			}
		}
		model.put("attackWeapon", attackWeapon);
		model.put("inRange", inRange);
		model.put("game", game);
		model.put("POVplayer", user);
		
		return "/game/gameboard";
	}
	
	@PostMapping(value = {"/game/attack/playerselected"})
	public String performAttack(@RequestParam("gameId") Integer gameId, @RequestParam("objectivePlayer") String objectiveName,
						@RequestParam("cardName") String cardName, Map<String, Object> model) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		RedCard attackWeapon = cardService.findRedCardByName(cardName).get();
		
		Player objective = game.getListPlayers().stream().filter(x -> x.getUser().getUsername().equals(objectiveName)).findFirst().get();
		Player attacker = game.getCurrentPlayer();
		
		//Falta hacer la parada por aqui
		
		//quitamos vida
		objective.setCurrentHearts(objective.getCurrentHearts()-attackWeapon.getDamage());
		//descartamos carta
		attacker.getHand().removeIf(x-> x.equals(attackWeapon));
		
		model.put("game", game);
		model.put("POVplayer", user);
		return "/game/gameboard";
	}

	@PostMapping(value = { "/game/end-turn" })
	public String endTurn(@RequestParam("gameId") Integer gameId, @RequestParam("currentPlayerId") Integer currentPlayerId, Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		Integer numPlayers = game.getListPlayers().size();
		Integer nextPlayerIndex = (game.getListPlayers().indexOf(game.getCurrentPlayer()) + 1) % numPlayers;
		game.setCurrentPlayer(game.getListPlayers().get(nextPlayerIndex));
		model.put("game", game);
		model.put("POVplayer", user);
		return "/game/gameboard";
	}

	@PostMapping(value = "/game/{gameId}/select/card/{cardId}")
	public String acceptController(@PathVariable("gameId") Integer gameId, @PathVariable("cardId") Integer cardId) {
		Optional<Card> card= cardService.findById(cardId);
		Game game=gameService.findById(gameId).get();
		Player p=game.getCurrentPlayer();
		if(card.get().getName().contains("armadura")) {
			gameService.statUp(p, "distanceBonus", 1);
		}else if(card.get().getName().contains("concretacion")){
			gameService.statUp(p, "damageBonus", 1);
		}else if(card.get().getName().contains("desenvainado")){
			gameService.statUp(p, "weaponBonus", 1);
		}
		p.getEquipment().add(card.get());
		
		return "redirect:/game/"+gameId;
	}

}
