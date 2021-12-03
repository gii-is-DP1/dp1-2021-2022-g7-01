package samuraisword.player;

import java.util.Collection;
import java.util.Map;

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
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

@Controller
public class PlayerController {	
	
	private final PlayerService playerService;
	
	@Autowired
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	@GetMapping(value = { "game/{id_game}/players/delete/{id_player}" })
	public String deletePlayersForm(@PathVariable("id_player") int idPlayer, Map<String, Object> model) {
		playerService.deletePlayer(idPlayer);
		return "redirect:/game/{id_game}";
	}
}
