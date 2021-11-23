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

import samuraisword.logros.Logro;
import samuraisword.logros.LogroService;
import samuraisword.logros.RolType;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

@Controller
public class PlayerController {
	
	private static final String FORM_PLAYER = "players/formPlayers";
	
	
	private final PlayerService playerService;
	private final UserService userService;
	
	@Autowired
	public PlayerController(PlayerService playerService, UserService userService) {
		this.playerService = playerService;
		this.userService = userService;
	}
	
	@GetMapping(value = { "players" })
	public String listLogros(Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Player> listPlayers = playerService.findAll();
		model.put("listPlayers", listPlayers);
		model.put("username", userDetails.getUsername());
		
		return "logros/listLogros";
	}
	
	@GetMapping(value = { "/players/new" })
	public String newLogroForm(Map<String, Object> model) {
		Player player = new Player();
		model.put("players", player);
		return FORM_PLAYER;
	}

	@Valid
	@PostMapping(value = "/players/new")
	public String processCreationForm(@Valid Player player, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("players", player);
			return FORM_PLAYER;
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			player.setUser(user);
			playerService.savePlayers(player);
			return "redirect:/logros";
		}
	}
	
	@ModelAttribute("types2")
	public Collection<RolType> populateRolTypes() {
		return this.playerService.findRolTypes();
	}
	
	@GetMapping(value = { "/players/edit/{id_player}" })
	public String editPlayerForm(@PathVariable("id_player") int idPlayer, Map<String, Object> model) {
		Player player = playerService.findById(idPlayer).get();
		model.put("players", player);
		return FORM_PLAYER;
	}
	
	@Valid
	@PostMapping(value = { "/players/edit/{id_player}" })
	public String processEditForm(@PathVariable("id_player") int idPlayer, @Valid Player player, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("players", player);
			return FORM_PLAYER;
		}
		Player playerToUpdate = playerService.findById(idPlayer).get();
		BeanUtils.copyProperties(player, playerToUpdate, "id","user");
		playerService.savePlayers(playerToUpdate);
		return "redirect:/players";
	}
	
	@GetMapping(value = { "/logros/delete/{id_player}" })
	public String deletePlayersForm(@PathVariable("id_player") int idPlayer, Map<String, Object> model) {
		playerService.deletePlayer(idPlayer);
		return "redirect:/players";
	}
	
	@GetMapping(value = { "/logros/deleteAll" })
	public String deletePlayers(Map<String, Object> model) {
		playerService.deleteAllPlayers();
		return "redirect:/players";
	}
	
	
//	private final PlayerService playerService;
//	private final UserService userService;
	
}
