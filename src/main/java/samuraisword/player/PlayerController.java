package samuraisword.player;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import samuraisword.achievements.Achievement;
import samuraisword.achievements.AchievementService;
import samuraisword.achievements.RolType;
import samuraisword.game.Game;
import samuraisword.game.GameService;
import samuraisword.game.GameSingleton;
import samuraisword.invitations.Invitation;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

@Controller
public class PlayerController {	
	
	private final PlayerService playerService;
	private final CardService cardService;
	private final GameService gameService;
	
	@Autowired
	public PlayerController(PlayerService playerService, CardService cardService, GameService gameService) {
		this.playerService = playerService;
		this.cardService = cardService;
		this.gameService = gameService;
	}
	
	@GetMapping(value = { "/players" })
	public String listAchievements(Map<String, Object> model) {
		Collection<Player> listPlayers = playerService.findAll();
		model.put("listPlayers", listPlayers);
		
		return "players/listPlayers";
	}
	
	@GetMapping(value = { "/game/{id_game}/players/delete/{id_player}" })
	public String deletePlayersForm(@PathVariable("id_player") int idPlayer, Map<String, Object> model) {
		Player player =playerService.findById(idPlayer).get();
		int id=player.getGame().getId();
		Game game=GameSingleton.getInstance().getMapGames().get(id);
		game.getListPlayers().remove(playerService.findById(idPlayer).get());
		playerService.deletePlayer(idPlayer);
		return "redirect:/game/{id_game}";
	}
	
	
}
