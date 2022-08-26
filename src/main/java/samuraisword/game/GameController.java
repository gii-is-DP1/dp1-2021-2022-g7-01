package samuraisword.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
		a.addHeader("Refresh", "3");

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
		if (!(GameSingleton.getInstance().getMapGames().containsKey(gameId))) {
			a.addHeader("Refresh", "3");
			Game game = gameService.findById(gameId).get();
			game.setGamePhase(GamePhase.LOBBY);
			model.put("now", new Date());
			model.put("listFriends", user.getListAllFriends());
			model.put("user", user.getUsername());
			model.put("listPlayer", game.getListPlayers());
			model.put("gameId", gameId);
			GameSingleton.getInstance().getMapGames().put(gameId, game);
			return "game/game";
		} else {
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if (game.getGamePhase().equals(GamePhase.LOBBY)) {
				a.addHeader("Refresh", "3");
				model.put("now", new Date());
				model.put("listFriends", user.getListAllFriends());
				model.put("user", user.getUsername());
				model.put("listPlayer", game.getListPlayers());
				model.put("gameId", gameId);
				GameSingleton.getInstance().getMapGames().put(gameId, game);
				return "game/game";
			}
			model.put("game", game);
			model.put("POVplayer", user);
			return "game/gameboard";
		}
	}

	@GetMapping(value = { "/game/delete/{id_game}" })
	public String deleteCommentForm(@PathVariable("id_game") int gameId, Map<String, Object> model) {
		gameService.deleteGame(gameId);
		return "redirect:/game/new";
	}

	@GetMapping(value = { "/game/start/{id_game}" })
	public String initGame(@PathVariable("id_game") int gameId, Map<String, Object> model) {
		String view = "redirect:/game/continue/"+gameId;
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);

		game.setGamePhase(GamePhase.MAIN);
		game.setDeck(cardService.createDeck());
		game.setDiscardPile(new ArrayList<>());

		List<Player> players = game.getListPlayers();

		// players de prueba
		Player p1 = playerService.findById(1).get();

		Player p2 = playerService.findById(2).get();

		Player p3 = playerService.findById(3).get();

		Player p4 = playerService.findById(4).get();

		Player p5 = playerService.findById(5).get();

		Player p6 = playerService.findById(6).get();

		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		players.add(p5);
		players.add(p6);

		gameService.asignCharacterAndHearts(players);
		gameService.asignRolAndHonor(players);
		gameService.asignOrder(players);

		game.setListPlayers(players);
		game.setCurrentPlayer(players.get(0));

		for (Player player : game.getListPlayers()) {
			player.setGame(game);
			playerService.savePlayer(player);
		}

		gameService.asignCards(game.getDeck(), players);

		game.setGamePhase(GamePhase.MAIN);
		return view;
	}

	@PostMapping(value = { "/game/end-turn" })
	public String endTurn(@RequestParam("gameId") Integer gameId, Map<String, Object> model) {
		String view = "redirect:/game/continue/"+gameId;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Game game = GameSingleton.getInstance().getMapGames().get(gameId);
		if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
			Boolean hasAdvancedPhase = gameService.endTurn(game);
	
			if (gameService.checkAllPlayersHavePositiveHonor(game)) {
				if (hasAdvancedPhase) {
					gameService.processRecoveryPhase(game);
					Boolean check = gameService.checkBushido(game);
					if(!check) {
						gameService.processDrawPhase(game);
					}
				}
			} else {// fin de la partida cuando algun jugador no le quedan puntos de honor
					// (honor<=0)
				view = "/game/endgame";
				Rol winnerRol = gameService.calcWinners(game);
				model.put("winnerRol", winnerRol);
			}
		}
		return view;
	}


	//--------------------------------------------------------------------------------------------------------------------------
		@GetMapping(value = {"/game/continue/{id_game}"})
		public String continueGame(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "/game/gameboard";
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			
			model.put("game", game);
			
			Player POVplayer = game.getListPlayers().stream()
					.filter(p -> p.getUser().getUsername().equals(user.getUsername()))
					.findFirst().get();
			model.put("POVplayer", POVplayer);
			model.put("gameStatus", game.getGamePhase().toString());
			return view;
		}

	
	//--------------------------------------------------------------------------------------------------------------------------
		
		@PostMapping(value = {"/game/use-card"})
		public String useCard(@RequestParam("gameId") Integer gameId, @RequestParam("cardName") String cardName, 
				Map<String, Object> model) {
			String view = "redirect:/game/"+cardName+"/"+gameId;
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				Optional<Card> card= cardService.findByName(cardName);
				if(card.get().getColor().equals("Blue")) {
					if(!cardName.equals("bushido")) {
						cardService.discard(cardName, game.getCurrentPlayer().getHand(), game.getCurrentPlayer().getEquipment());
					}
					else {
						Boolean someoneHasBushido = false;
						for(int i=0; i<game.getListPlayers().size(); i++) {
							for(int o=0; o<game.getListPlayers().get(i).getEquipment().size(); o++) {
								if(game.getListPlayers().get(i).getEquipment().get(o).getName().equals("bushido")) {
									someoneHasBushido=true;
								}
							}
						}
						if(!someoneHasBushido) {
							cardService.discard(cardName, game.getCurrentPlayer().getHand(), game.getCurrentPlayer().getEquipment());
						}
						else {
							view = "redirect:/game/continue/"+gameId;
						}
					}
				}else {
					cardService.discard(cardName, game.getCurrentPlayer().getHand(), game.getDiscardPile());
				}
			}

			
			return view;
		}
		
		//-------------------------------------------------------------------------------------------------------------Rojo
		@GetMapping(value = {"/game/bo/{id_game}"})
		public String boCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/bokken/{id_game}"})
		public String bokkenCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/daiku/{id_game}"})
		public String daikuCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/kanabo/{id_game}"})
		public String kanaboCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/katana/{id_game}"})
		public String katanaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/kiseru/{id_game}"})
		public String kiseruCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/kusarigama/{id_game}"})
		public String kusarigamaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/nagayari/{id_game}"})
		public String nagayariCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/naginata/{id_game}"})
		public String naginataCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/nodachi/{id_game}"})
		public String nodachiCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/shuriken/{id_game}"})
		public String shurikenCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/tanegashima/{id_game}"})
		public String tanegashimaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/wakizashi/{id_game}"})
		public String wakizashiCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		//--------------------------------------------------------------------------------------------------------Amarillo
		
		@GetMapping(value = {"/game/ceremonia del te/{id_game}"})
        public String ceremoniaDelTeCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
            String view = "redirect:/game/continue/"+gameId;
            //----------Aqui va el método
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				Player myPlayer= game.getCurrentPlayer();
	
	            gameService.proceesDrawPhasePlayer(game, myPlayer, 3);
	
	
	            List<Player> allOpponents= game.getListPlayers();
	            allOpponents.remove(myPlayer);
	
	            for(Player pl:allOpponents) {
	                gameService.proceesDrawPhasePlayer(game, pl, 1);
	            }
			}

            return view;
        }
		
		@GetMapping(value = {"/game/daimio/{id_game}"})
		public String daimioCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/distraccion/{id_game}"})
		public String distraccionCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/geisha/{id_game}"})
		public String geishaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				game.setGamePhase(GamePhase.DISCARDOTHER);
			}
			return view;
		}
		
		@GetMapping(value = {"/game/grito de batalla/{id_game}"})
		public String gritoDeBatallaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/jiu-jitsu/{id_game}"})
		public String jiuJitsuCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/parada/{id_game}"})
		public String paradaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/respiracion/{id_game}"})
		public String respiracionCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		//--------------------------------------------------------------------------------------------------------Azul
		@GetMapping(value = {"/game/armadura/{id_game}"})
		public String armaduraCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				Player p = game.getCurrentPlayer();
				p.setDistanceBonus(p.getDistanceBonus()+1);
			}
			
			return view;
		}
		
		@GetMapping(value = {"/game/bushido/{id_game}"})
		public String bushidoCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				game.setGamePhase(GamePhase.BUSHIDO);
			}
			return view;
		}
		
		@GetMapping(value = {"/game/concentracion/{id_game}"})
		public String concentracionCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				Player p = game.getCurrentPlayer();
				p.setWeaponBonus(p.getWeaponBonus()+1);
			}
			return view;
		}
		
		@GetMapping(value = {"/game/desenvainado rapido/{id_game}"})
		public String desenvainadoRapidoCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				Player p = game.getCurrentPlayer();
				p.setDamageBonus(p.getDamageBonus()+1);
			}
			return view;
		}
		
		@PostMapping(value = {"/game/discard-hand-card"})
		public String discardHandCard(@RequestParam("gameId") Integer gameId, @RequestParam("cardName") String cardName, 
				Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				view = endTurnPostDiscard(gameId,model);
				cardService.discard(cardName, game.getCurrentPlayer().getHand(), game.getDiscardPile());
			}
			return view;
		}
		
		//----------------------------------------------------------------------------------------
		
		public String endTurnPostDiscard(Integer gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			Boolean hasAdvancedPhase = gameService.endTurn(game);

			if (gameService.checkAllPlayersHavePositiveHonor(game)) {
				if (hasAdvancedPhase) {
					gameService.processRecoveryPhase(game);
					Boolean check = gameService.checkBushido(game);
					if(!check) {
						gameService.processDrawPhase(game);
					}
				}
			} else {// fin de la partida cuando algun jugador no le quedan puntos de honor
					// (honor<=0)
				view = "/game/endgame";
				Rol winnerRol = gameService.calcWinners(game);
				model.put("winnerRol", winnerRol);
			}

			return view;
		}
		
		@PostMapping(value = {"/game/discard-other-card"})
		public String discardFrom(@RequestParam("gameId") Integer gameId, @RequestParam("cardName") String cardName, 
				@RequestParam("player") String player, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				Player p = new Player();
				for(int i=0; i<game.getListPlayers().size(); i++) {
					if(game.getListPlayers().get(i).getUser().getUsername().equals(player)) {
						p = game.getListPlayers().get(i);
					}
				}
				switch (cardName) {
				case "hand":
					List<Card>lh = p.getHand();
					if(lh.size()!=0) {
						game.setGamePhase(GamePhase.MAIN);
						int i = 0 + (int)(Math.random() * ((lh.size() - 0) + 1));
						cardService.discard(lh.get(i).getName(), p.getHand(), game.getDiscardPile());
					}
					break;
				case "armadura":
					if(p.getDistanceBonus()!=0) {
						p.setDistanceBonus(p.getDistanceBonus()-1);
						game.setGamePhase(GamePhase.MAIN);
						cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
					}
					break;
				case "concentracion":
					if(p.getWeaponBonus()!=0) {
						p.setWeaponBonus(p.getWeaponBonus()-1);
						game.setGamePhase(GamePhase.MAIN);
						cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
					}
					break;
				case "desenvainado rapido":
					if(p.getWeaponBonus()!=0) {
						p.setDamageBonus(p.getDamageBonus()+1);
						game.setGamePhase(GamePhase.MAIN);
						cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
					}
					break;
				default:
					break;
				}
			}
			return view;
		}
		@PostMapping(value = {"/game/equip-bushido"})
		public String bushidoEquipFrom(@RequestParam("gameId") Integer gameId, 
				@RequestParam("player") String player, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(user.getUsername().equals(game.getCurrentPlayer().getUser().getUsername())) {
				Player p = new Player();
				for(int i=0; i<game.getListPlayers().size(); i++) {
					if(game.getListPlayers().get(i).getUser().getUsername().equals(player)) {
						p = game.getListPlayers().get(i);
					}
				}
				cardService.discard("bushido", game.getCurrentPlayer().getEquipment(), p.getEquipment());
				game.setGamePhase(GamePhase.MAIN);
			}
			return view;
		}
		@PostMapping(value = {"/game/pass-bushido"})
		public String bushidoDiscardFrom(@RequestParam("gameId") Integer gameId, 
				@RequestParam("card") String card, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(card.equals("NONE")) {
				game.getCurrentPlayer().setHonor(game.getCurrentPlayer().getHonor()-1);
				cardService.discard(card, game.getCurrentPlayer().getEquipment(), game.getDiscardPile());
			}
			else {
				Integer numPlayers = game.getListPlayers().size();
				Integer nextPlayerIndex = (game.getListPlayers().indexOf(game.getCurrentPlayer()) + 1) % numPlayers;
				cardService.discard(card, game.getCurrentPlayer().getHand(), game.getDiscardPile());
				cardService.discard("bushido", game.getCurrentPlayer().getEquipment(), game.getListPlayers().get(nextPlayerIndex).getEquipment());
			}
			gameService.processDrawPhase(game);
			game.setGamePhase(GamePhase.MAIN);
			return view;
		}
}
