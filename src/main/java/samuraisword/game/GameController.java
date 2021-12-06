package samuraisword.game;


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

import samuraisword.cardhand.CardHand;
import samuraisword.cardhand.CardHandService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
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
 	
	private static final String VIEWS_CREATE_GAME = "game/createGame";
	
	@Autowired
	public GameController(GameService GameService, UserService userService, PlayerService playerService, CardHandService cardHandService,
				CardService cardService) {
		this.gameService = GameService;
		this.userService = userService;
		this.playerService = playerService;
		this.cardHandService = cardHandService;
		this.cardService = cardService;
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
	public String initGame(@PathVariable("id_game") int gameId,  Map<String, Object> model, HttpServletResponse a) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game=gameService.findById(gameId).get();
		a.addHeader("Refresh", "1");
		
		CardHand gameDeck = new CardHand();//funcion que crea el deck predeterminado y ordenado aleatoriamente		
		cardHandService.save(gameDeck);
		
		RedCard bo = RedCard.of("bo", "attack/bo.png", 2, 1);
		gameDeck.setCardList(List.of(bo));
		cardService.saveCard(bo);
		
		
		model.put("user", user.getUsername());
		model.put("listPlayer", game.getListPlayers());
		model.put("gameId", gameId);
		model.put("gameDeck", gameDeck);
		
		return "/game/gameboard";
	}
	
	
	
	

}
