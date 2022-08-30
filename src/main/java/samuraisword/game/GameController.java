package samuraisword.game;

import java.util.ArrayList;
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
import samuraisword.cardhand.CardHand;
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
		game.setGamePhase(GamePhase.LOBBY);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Player player = new Player();
		player.setUser(user);
		player.setGame(game);
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(player);
		game.setListPlayers(listPlayers);
		gameService.saveGame(game);
		userService.saveUser(user);
		playerService.savePlayer(player);
		GameSingleton.getInstance().getMapGames().put(game.getId(), game);
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
				return "game/game";
			}
			return "redirect:/game/continue/"+gameId;
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

		gameService.asignCharacterAndHearts(players);
		gameService.asignRolAndHonor(players);
		gameService.asignOrder(players);
		

		game.setListPlayers(players);
		game.setCurrentPlayer(players.get(0));
		game.getCurrentPlayer().setWeaponBonus(1);
	

		for (Player player : game.getListPlayers()) {
			player.setGame(game);
			player.setEquipment(new ArrayList<>());
			playerService.savePlayer(player);
		}

		gameService.asignCards(game.getDeck(), players);
		gameService.processDrawPhase(game);
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
			game.getCurrentPlayer().setWeaponBonus(1);
			for(int i=0; i<game.getCurrentPlayer().getEquipment().size();i++) {
				if(game.getCurrentPlayer().getEquipment().get(i).getName().equals("concentracion")) {
					game.getCurrentPlayer().setWeaponBonus(game.getCurrentPlayer().getWeaponBonus()+1);
				}
			}
			if(game.getCurrentPlayer().getCharacter().getName().equals("Goemon")) {
				game.getCurrentPlayer().setWeaponBonus(game.getCurrentPlayer().getWeaponBonus()+1);
			}
			if (gameService.checkAllPlayersHavePositiveHonor(game)) {
				if (hasAdvancedPhase) {
					gameService.processRecoveryPhase(game);
					List<Boolean> checks = gameService.checkBushido(game);
					Boolean check = checks.get(0);
					Boolean endGame = checks.get(1);
					if(endGame) {
						return this.endGame(game, model);
					}
					if(!check) {
						endGame = gameService.processDrawPhase(game);
						if(endGame) {
							return this.endGame(game, model);
						}
					  if(game.getCurrentPlayer().getCharacter().getName().equals("Hideyoshi")) {
						  endGame = gameService.checkGameDeck(game);
						  if(endGame) {
								return this.endGame(game, model);
							}
						  Card card = game.getDeck().get(0);
              game.getCurrentPlayer().getHand().add(card);
              game.getDeck().remove(0);
            }
					}
				}
			} else {// fin de la partida cuando algun jugador no le quedan puntos de honor
					// (honor<=0)
				view = endGame(game, model);
				Rol winnerRol = gameService.calcWinners(game);
				game.setWonPlayers(new ArrayList<User>());
				for(Player p: game.getListPlayers()) {
					if(p.getRol().equals(winnerRol) || (winnerRol.equals(Rol.SAMURAI) && p.getRol().equals(Rol.SHOGUN))) {
					game.getWonPlayers().add(p.getUser());
					}
        }
				gameService.saveGame(game);
			}
		}
		return view;
	}
	
	public String endGame(Game game, Map<String, Object> model) {
		Rol winnerRol = gameService.calcWinners(game);
		model.put("winnerRol", winnerRol);
		return "/game/endgame";
	}

	//--------------------------------------------------------------------------------------------------------------------------
		@GetMapping(value = {"/game/continue/{id_game}"})
		public String continueGame(@PathVariable("id_game") int gameId, Map<String, Object> model, HttpServletResponse a) {
			
			String view = "/game/gameboard";
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(!gameService.checkAllPlayersHavePositiveHonor(game)) {
				view = endGame(game, model);
				Rol winnerRol = gameService.calcWinners(game);
				game.setWonPlayers(new ArrayList<User>());
				for(Player p: game.getListPlayers()) {
					if(p.getRol().equals(winnerRol) || (winnerRol.equals(Rol.SAMURAI) && p.getRol().equals(Rol.SHOGUN))) {
					game.getWonPlayers().add(p.getUser());
					}
        }
				gameService.saveGame(game);
				return view;
			}
			for(int i=0; i<game.getListPlayers().size();i++) {
				game.getListPlayers().get(i).setNArmor(0);
				game.getListPlayers().get(i).setNFastDraw(0);
				game.getListPlayers().get(i).setNBushido(0);
				game.getListPlayers().get(i).setNFocus(0);
				for(int o=0; o<game.getListPlayers().get(i).getEquipment().size();o++) {
					if(game.getListPlayers().get(i).getEquipment().get(o).getName().equals("armadura")) {
						game.getListPlayers().get(i).setNArmor(game.getListPlayers().get(i).getNArmor()+1);
					}if(game.getListPlayers().get(i).getEquipment().get(o).getName().equals("desenvainado rapido")) {
						game.getListPlayers().get(i).setNFastDraw(game.getListPlayers().get(i).getNFastDraw()+1);
					}if(game.getListPlayers().get(i).getEquipment().get(o).getName().equals("concentracion")) {
						game.getListPlayers().get(i).setNFocus(game.getListPlayers().get(i).getNFocus()+1);
					}if(game.getListPlayers().get(i).getEquipment().get(o).getName().equals("bushido")) {
						game.getListPlayers().get(i).setNBushido(game.getListPlayers().get(i).getNBushido()+1);
					}
				}
			}
			
			model.put("game", game);
			
			Player POVplayer = game.getListPlayers().stream()
					.filter(p -> p.getUser().getUsername().equals(user.getUsername()))
					.findFirst().get();
			model.put("POVplayer", POVplayer);
			model.put("gameStatus", game.getGamePhase().toString());
			String discardImage;
			if(game.getDiscardPile().isEmpty()) {
				discardImage = "/resources/images/roles/ninguno.png";
			} else {
				discardImage = "/resources/images/cards/" + game.getDiscardPile().get(game.getDiscardPile().size() - 1).getName() + ".png";
			}
			model.put("discardImage", discardImage);
			return view;
		}

	
	//--------------------------------------------------------------------------------------------------------------------------
		
		
		
		
		
		@PostMapping(value = {"/game/use-card"})
		public String useCard(@RequestParam("gameId") Integer gameId, @RequestParam("cardName") String cardName, 
				Map<String, Object> model) {
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			String view = "redirect:/game/"+cardName+"/"+gameId;
			if(cardName.equals("no-selected")) {
				game.setError("No has seleccionado ninguna carta");
				return "redirect:/game/continue/"+gameId;
			}
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			game.setUseCard(cardService.findByName(cardName).get());
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
					if(card.get().getColor().equals("Red")) {
						
						
						if(game.getCurrentPlayer().getWeaponBonus()>0) {
							game.getCurrentPlayer().setWeaponBonus(game.getCurrentPlayer().getWeaponBonus()-1);
							cardService.discard(cardName, game.getCurrentPlayer().getHand(), game.getDiscardPile());
						}else {
							view = "redirect:/game/continue/"+gameId;
						}
					}else {
					cardService.discard(cardName, game.getCurrentPlayer().getHand(), game.getDiscardPile());
					}
				}
			}
			return view;
		}
		
		
		
		
		
		
		
		//-------------------------------------------------------------------------------------------------------------Rojo
		@GetMapping(value = {"/game/bo/{id_game}"})
		public String boCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("bo").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			
			
			return view;
		}
		
		@GetMapping(value = {"/game/bokken/{id_game}"})
		public String bokkenCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("bokken").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
			if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
				
				lp.remove(i);
				}
			}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@PostMapping(value = {"/game/attack/{id_game}/{playerA}"}) 
		public String DoDamage(@PathVariable("id_game") int gameId, @PathVariable("playerA") String playerA ,Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			Card c= game.getUseCard();
			Player p=playerService.findPlayerByUsernameAndGame(playerA, game);
			int dano=cardService.findDamage(game.getUseCard().getName()).get();
			game.setAttackerDamage(dano+game.getCurrentPlayer().getDamageBonus());
			game.setAttackerPlayer(p);
			int i=cardService.findDamage(c.getName()).get()+game.getCurrentPlayer().getDamageBonus();
			for(int i2=0;i2<game.getListPlayers().size();i2++) {
				if(game.getListPlayers().get(i2).getUser().getUsername().equals(playerA)) {
					if(i>1) {
					game.getListPlayers().get(i2).setCurrentHearts(game.getListPlayers().get(i2).getCurrentHearts()-i+game.getListPlayers().get(i2).getAntiDamageBonus());
					}else{
						game.getListPlayers().get(i2).setCurrentHearts(game.getListPlayers().get(i2).getCurrentHearts()-i);	
					}
					if(game.getCurrentPlayer().getCharacter().getName().equals("Tomoe")) {
						Card card = game.getDeck().get(0);
						game.getCurrentPlayer().getHand().add(card);
						game.getDeck().remove(0);
					}
					if(game.getListPlayers().get(i2).getCurrentHearts()<=0) {
						if(game.getListPlayers().get(i2).getHonor()>0) {
//							game.getListPlayers().get(i2).setCurrentHearts(game.getListPlayers().get(i2).getCharacter().getLife());
							game.getListPlayers().get(i2).setHonor(game.getListPlayers().get(i2).getHonor()-1);
							game.getCurrentPlayer().setHonor(game.getCurrentPlayer().getHonor()+1);
						}
					}
				}
			}
			game.setGamePhase(GamePhase.AVISO);
			return view;
		}
		
		
		@PostMapping(value = {"/game/selectPlayer/{id_game}/{playerA}"}) 
		public String selectPlayer(@PathVariable("id_game") int gameId, @PathVariable("playerA") String playerA ,Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(playerA.equals("none")) {
				
				cardService.discard(game.getUseCard().getName(), game.getDiscardPile(), game.getCurrentPlayer().getHand());
				game.setGamePhase(GamePhase.MAIN);
				game.getCurrentPlayer().setWeaponBonus(game.getCurrentPlayer().getWeaponBonus()+1);
			}else {
			Player p=playerService.findPlayerByUsernameAndGame(playerA, game);
			for(int i=0;i<p.getHand().size();i++) {
				if(p.getHand().get(i).getName().equals("parada")) {
					game.setAttackerPlayer(p);
					game.setGamePhase(GamePhase.PARADA); 
				}
		
			} 
			
			if(!(game.getGamePhase().equals(GamePhase.PARADA))){
				
				 DoDamage(gameId, playerA, model);
			}
			}
			return view;
		}
		
		@PostMapping(value = {"/game/aviso/{id_game}"}) 
		public String aviso(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			game.setGamePhase(GamePhase.MAIN);
			
			return view;
		}
		
		
		@PostMapping(value = {"/game/parada/{id_game}/{playerA}"}) 
		public String parada(@PathVariable("id_game") int gameId, @PathVariable("playerA") String playerA ,Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			game.setGamePhase(GamePhase.MAIN);
			Player p=playerService.findPlayerByUsernameAndGame(playerA, game);
			
			
			cardService.discard("parada", p.getHand(), game.getDiscardPile());
				
			return view;
		}
		
		
		@GetMapping(value = {"/game/daiku/{id_game}"}) 
		public String daikuCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("daiku").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/kanabo/{id_game}"})
		public String kanaboCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("kanabo").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/katana/{id_game}"})
		public String katanaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("katana").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/kiseru/{id_game}"})
		public String kiseruCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("kiseru").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/kusarigama/{id_game}"})
		public String kusarigamaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("kusarigama").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/nagayari/{id_game}"})
		public String nagayariCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("nagayari").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/naginata/{id_game}"})
		public String naginataCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("naginata").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/nodachi/{id_game}"})
		public String nodachiCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("nodachi").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/shuriken/{id_game}"})
		public String shurikenCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("shuriken").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/tanegashima/{id_game}"})
		public String tanegashimaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId); 

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("tanegashima").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		@GetMapping(value = {"/game/wakizashi/{id_game}"})
		public String wakizashiCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);

			Player POVplayer = game.getCurrentPlayer();
			
			List<Player> lp=gameService.playersInRangeOfAttack(game, cardService.findRedCardByName("wakizashi").get(), POVplayer);
			for(int i=0;i<lp.size();i++) {
				if(lp.get(i).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
					lp.remove(i);
				}
				
				}
			game.setPlayersInRange(lp);
			game.setGamePhase(GamePhase.ATTACK);
			return view;
		}
		
		//--------------------------------------------------------------------------------------------------------Amarillo
		
		@GetMapping(value = {"/game/ceremonia del te/{id_game}"})
    public String ceremoniaDelTeCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
            String view = "redirect:/game/continue/"+gameId;
            //----------Aqui va el método
            Game game=GameSingleton.getInstance().getMapGames().get(gameId);
            model.put("game",game);
            Player myPlayer= game.getCurrentPlayer();

            Boolean endGame = gameService.proceesDrawPhasePlayer(game, myPlayer, 3);
            if(endGame) {
            	return endGame(game, model);
            }

            List<Player> allOpponents= new ArrayList<>(game.getListPlayers());
            allOpponents.remove(myPlayer);

            for(Player pl:allOpponents) {
            	endGame = gameService.proceesDrawPhasePlayer(game, pl, 1);
                if(endGame) {
                	return endGame(game, model);
                }
            }

            return view;
        }
		
		@GetMapping(value = {"/game/daimio/{id_game}"})
		public String daimioCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			Game game= GameSingleton.getInstance().getMapGames().get(gameId);
			model.put("game",game);
			Player myPlayer= game.getCurrentPlayer();
			
			Boolean endGame = gameService.proceesDrawPhasePlayer(game, myPlayer, 2);
            if(endGame) {
            	return endGame(game, model);
            }
			
			return view;
		}
		
		@GetMapping(value = {"/game/distraccion/{id_game}"})
        public String distraccionCard(@PathVariable("id_game") int gameId, 
                Map<String, Object> model) {
            String view = "redirect:/game/continue/"+gameId;
            //----------Aqui va el método
            Game game= GameSingleton.getInstance().getMapGames().get(gameId);
            game.setGamePhase(GamePhase.DISCARDPLAYER);


            return view;
        }

        @PostMapping(value = {"/game/distraccion/{id_game}/{playerName}"})
        public String distraccionCard(@PathVariable("id_game") int gameId, @PathVariable("playerName") String playerName,
                Map<String, Object> model) {
            String view = "redirect:/game/continue/"+gameId;
            //----------Aqui va el método
            Game game= GameSingleton.getInstance().getMapGames().get(gameId);
            model.put("game",game);
            Player myPlayer= game.getCurrentPlayer();

            Player opponent= gameService.findPlayerInGameByName(game, playerName);

            Random r = new Random();
            int valorDado = r.nextInt(opponent.getHand().size());
            List<Card> handOpponent= opponent.getHand();
            myPlayer.getHand().add(handOpponent.get(valorDado));
            handOpponent.remove(valorDado);
            game.setGamePhase(GamePhase.MAIN);




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
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			
			for(int i=0;i<game.getListPlayers().size();i++) {
				for(int a=0; a<game.getListPlayers().get(i).getHand().size();a++) {
					if(game.getListPlayers().get(i).getHand().get(a).equals(cardService.findByName("parada").get())) {
						game.getListPlayers().get(i).setHaveParada(true);
						break;
					}else {
						game.getListPlayers().get(i).setHaveParada(false);
					}
					
				}
				
				if(game.getListPlayers().get(i).getHand().size()==0 || game.getListPlayers().get(i).getCurrentHearts()<=0) {
					game.getListPlayers().get(i).setIndefence(true);
					
				}else {
					game.getListPlayers().get(i).setIndefence(false);
				}
			}
			
			List<Player>lp=new ArrayList<>();
			game.setWaitingForPlayer(lp);
			for(int a=0;a<game.getListPlayers().size();a++) {
				if(!(game.getListPlayers().get(a).equals(game.getCurrentPlayer()))) {
					game.getWaitingForPlayer().add(game.getListPlayers().get(a));
				}
				if(game.getListPlayers().get(a).getHand().size()==0 || game.getListPlayers().get(a).getCurrentHearts()<=0
						|| game.getListPlayers().get(a).getCharacter().getName().equals("Chiyome")) {
					game.getWaitingForPlayer().remove(game.getListPlayers().get(a));
				}
			}
			
			if(game.getUseCard().equals(cardService.findByName("grito de batalla").get())) {
				game.setGamePhase(GamePhase.GRITODEBATALLA);
			}
			
			
			return view;
		}
		
		@PostMapping(value = {"/game/choose1/{id_game}/{playerA}"})
		public String choose1(@PathVariable("id_game") int gameId, @PathVariable("playerA") String playerA,Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			
			Player p=playerService.findPlayerByUsernameAndGame(playerA, game);
			p.setCurrentHearts(p.getCurrentHearts()-1);
			if(p.getCurrentHearts()<=0) {
				if(p.getHonor()>0) {
				//	p.setCurrentHearts(p.getCharacter().getLife());
					p.setHonor(p.getHonor()-1);
					game.getCurrentPlayer().setHonor(game.getCurrentPlayer().getHonor()+1);
				}
			}
			game.getWaitingForPlayer().remove(p);
			if(game.getWaitingForPlayer().size()==0) {
				game.setGamePhase(GamePhase.MAIN);
			}
			return view;
		}
		
		@PostMapping(value = {"/game/choose2/{id_game}/{playerA}"})
		public String choose2(@PathVariable("id_game") int gameId, @PathVariable("playerA") String playerA, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			
		
			Player p=playerService.findPlayerByUsernameAndGame(playerA, game);
//			for(int i=0;i<p.getHand().size();i++)
//			if(p.getHand().get(i).equals(cardService.findByName("parada").get())) {
//				p.setHaveParada(true);
//			}else {
//				p.setHaveParada(false);
//			}
			cardService.discard("parada", p.getHand(), game.getDiscardPile());
			game.getWaitingForPlayer().remove(p);
			if(game.getWaitingForPlayer().size()==0) {
				game.setGamePhase(GamePhase.MAIN);
			}
			return view;
		}
		
		@GetMapping(value = {"/game/jiu-jitsu/{id_game}"})
		public String jiuJitsuCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			
			List<Player>lp=new ArrayList<>();
			game.setWaitingForPlayer(lp);
			for(int a=0;a<game.getListPlayers().size();a++) {
				if(!(game.getListPlayers().get(a).equals(game.getCurrentPlayer()))) {
					game.getWaitingForPlayer().add(game.getListPlayers().get(a));
				}
			}
			for(int a=0;a<game.getListPlayers().size();a++) {
			for(int i=0;i<game.getListPlayers().get(a).getHand().size();i++) {
				if(game.getListPlayers().get(a).getHand().get(i).getColor().equals("Red")) {
					game.getListPlayers().get(a).setHaveRedCard(true);
					break;
				}else {
					game.getListPlayers().get(a).setHaveRedCard(false);
				}
			}
			if(game.getListPlayers().get(a).getHand().size()==0 || game.getListPlayers().get(a).getCurrentHearts()<=0) {
				game.getListPlayers().get(a).setIndefence(true);
				game.getWaitingForPlayer().remove(game.getListPlayers().get(a));
			}else {
				if(game.getListPlayers().get(a).getCharacter().getName().equals("Chiyome")) {
					game.getWaitingForPlayer().remove(game.getListPlayers().get(a));
				}
				game.getListPlayers().get(a).setIndefence(false);
			}
				
			}
			if(game.getUseCard().equals(cardService.findByName("jiu-jitsu").get())) {
				game.setGamePhase(GamePhase.JIUJITSU);
			}
			return view;
		}
		
		@PostMapping(value = {"/game/choose11/{id_game}/{playerA}"})
		public String choose11(@PathVariable("id_game") int gameId, @PathVariable("playerA") String playerA,Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			Player p=playerService.findPlayerByUsernameAndGame(playerA, game);
			p.setCurrentHearts(p.getCurrentHearts()-1);
			if(p.getCurrentHearts()<=0) {
				if(p.getHonor()>0) {
			//		p.setCurrentHearts(p.getCharacter().getLife());
					p.setHonor(p.getHonor()-1);
					game.getCurrentPlayer().setHonor(game.getCurrentPlayer().getHonor()+1);
				}
			}
			game.getWaitingForPlayer().remove(p);
			if(game.getWaitingForPlayer().size()==0) {
				game.setGamePhase(GamePhase.MAIN);
			}
			return view;
		}
		
		
		
		
		@PostMapping(value = {"/game/choose21/{cardName}/{id_game}/{playerA}"})
		public String choose21(@PathVariable("id_game") int gameId, @PathVariable("cardName") String cardName, @PathVariable("playerA") String playerA, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			
			Player p=playerService.findPlayerByUsernameAndGame(playerA, game);
			
			cardService.discard(cardName, p.getHand(), game.getDiscardPile());
			game.getWaitingForPlayer().remove(p);
			if(game.getWaitingForPlayer().size()==0) {
				game.setGamePhase(GamePhase.MAIN);
			}
//			for(int i=0;i<p.getHand().size();i++)
//			if(p.getHand().get(i).equals(cardService.findByName("parada").get())) {
//				p.setHaveParada(true);
//			}else {
//				p.setHaveParada(false);
//			}
			
		
			
			return view;
		}
		
		
		
		
		@PostMapping(value = {"/game/deleteCard/{cardName}/{id_game}/{playerA}"})
		public String deletecard(@PathVariable("cardName") String cardName, @PathVariable("id_game") int gameId, @PathVariable("playerA") String playerA, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			
			Player p=playerService.findPlayerByUsernameAndGame(playerA, game);
			
			p.getHand().remove(cardService.findByName(cardName).get());
			game.getWaitingForPlayer().remove(p);
			
			if(game.getWaitingForPlayer().size()==0) {
				game.setGamePhase(GamePhase.MAIN);
			}else {
				game.setGamePhase(GamePhase.JIUJITSU);
			}
			
			return view;
		}
		
		@GetMapping(value = {"/game/parada/{id_game}"})
		public String paradaCard(@PathVariable("id_game") int gameId, Map<String, Object> model) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			
			return view;
		}
		
		@GetMapping(value = {"/game/respiracion/{id_game}"})
		public String respiracionCard(@PathVariable("id_game") int gameId) {
			String view = "redirect:/game/continue/"+gameId;
			//----------Aqui va el método
			Game game= GameSingleton.getInstance().getMapGames().get(gameId);
			game.setGamePhase(GamePhase.RESPIRACION);
			return view;
		}
		
		@PostMapping(value= {"/game/respiracion/{id_game}/{playerName}"})
		public String respiracionCard(@PathVariable("id_game") int gameId,@PathVariable("playerName") String playerName,
				Map<String,Object> model) {
		String view = "redirect:/game/continue/"+gameId;
		Game game= GameSingleton.getInstance().getMapGames().get(gameId);
		model.put("game",game);
		Player myPlayer= game.getCurrentPlayer();
		Player opponent= gameService.findPlayerInGameByName(game, playerName);
		
		Boolean endGame = gameService.proceesDrawPhasePlayer(game, opponent, 1);
        if(endGame) {
        	return endGame(game, model);
        }
		Integer maxLife= myPlayer.getCharacter().getLife();
		
		if(myPlayer.getCurrentHearts() !=maxLife) {
			myPlayer.setCurrentHearts(maxLife);
		}
		game.setGamePhase(GamePhase.MAIN);
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
			Game game = GameSingleton.getInstance().getMapGames().get(gameId);
			if(cardName.equals("no-selected")) {
				game.setError("No has seleccionado ninguna carta");
				return "redirect:/game/continue/"+gameId;
			}
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
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
					List<Boolean> checks = gameService.checkBushido(game);
					Boolean check = checks.get(0);
					Boolean endGame = checks.get(1);
					if(endGame) {
						return this.endGame(game, model);
					}
					if(!check) {
						endGame = gameService.processDrawPhase(game);
						if(endGame) {
							return this.endGame(game, model);
						}
						if(game.getCurrentPlayer().getCharacter().getName().equals("Hideyoshi")) {
						  endGame = gameService.checkGameDeck(game);
						  if(endGame) {
							return this.endGame(game, model);
						  }
						  Card card = game.getDeck().get(0);
						  game.getCurrentPlayer().getHand().add(card);
						  game.getDeck().remove(0);
						}
					}
				}
			} else {// fin de la partida cuando algun jugador no le quedan puntos de honor
					// (honor<=0)
				return this.endGame(game, model);
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
					if(lh.size()>0) {
						game.setGamePhase(GamePhase.MAIN);
						int i = 0 + (int)(Math.random() * ((lh.size() - 0)));
						cardService.discard(lh.get(i).getName(), p.getHand(), game.getDiscardPile());
					} else {
						game.setError("No tiene cartas en mano.");
					}
					break;
				case "armadura":
					if(p.getDistanceBonus()>0) {
						if(!(p.getCharacter().getName().equals("Benkei") && p.getDistanceBonus()==1)) {
							p.setDistanceBonus(p.getDistanceBonus()-1);
							game.setGamePhase(GamePhase.MAIN);
							cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
						}
					} else {
						game.setError("No tiene ninguna armadura equipada.");
					}
					break;
				case "concentracion":
					if(p.getWeaponBonus()>0) {
						if(!(p.getCharacter().getName().equals("Goemon") && p.getWeaponBonus()==1)) {
							p.setWeaponBonus(p.getWeaponBonus()-1);
							game.setGamePhase(GamePhase.MAIN);
							cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
						}
					} else {
						game.setError("No tiene ninguna concentración equipada.");
					}
					break;
				case "desenvainado rapido":
					if(p.getWeaponBonus()>0) {
						if(!(p.getCharacter().getName().equals("Musashi") && p.getDamageBonus()==1)) {
							p.setDamageBonus(p.getDamageBonus()+1);
							game.setGamePhase(GamePhase.MAIN);
							cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
						}
						
					} else {
						game.setError("No tiene ningún desenvainado rápido equipado.");
					}
					break;
				default:
					game.setError("No ha seleccionado correctamente.");
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
				cardService.discard("bushido", game.getCurrentPlayer().getEquipment(), game.getDiscardPile());
			}
			else {
				Integer numPlayers = game.getListPlayers().size();
				Integer nextPlayerIndex = (game.getListPlayers().indexOf(game.getCurrentPlayer()) + 1) % numPlayers;
				cardService.discard(card, game.getCurrentPlayer().getHand(), game.getDiscardPile());
				cardService.discard("bushido", game.getCurrentPlayer().getEquipment(), game.getListPlayers().get(nextPlayerIndex).getEquipment());
			}
			Boolean endGame = gameService.processDrawPhase(game);
			if(endGame) {
				return this.endGame(game, model);
			}
      if(game.getCurrentPlayer().getCharacter().getName().equals("Hideyoshi")) {
    	  endGame = gameService.checkGameDeck(game);
    	  if(endGame) {
				return this.endGame(game, model);
			}
    	  Card drawCard = game.getDeck().get(0);
    	  game.getCurrentPlayer().getHand().add(drawCard);
    	  game.getDeck().remove(0);
      }
			game.setGamePhase(GamePhase.MAIN);
			return view;
		}
}
