package samuraisword.player;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import samuraisword.logros.Logro;
import samuraisword.logros.LogroService;
import samuraisword.samples.petclinic.user.UserService;

@Controller
@RequestMapping("/players")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;

	@GetMapping()
	public String playerList(Map<String, Object> model) {
		Collection<Player> playerList = playerService.findAll();
		model.put("playerList", playerList);
		return "players/playerlist";
	}
	
//	private final PlayerService playerService;
//	private final UserService userService;
	
}
