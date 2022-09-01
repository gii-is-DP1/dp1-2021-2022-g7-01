package samuraisword.samples.petclinic.user;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import samuraisword.achievements.RolType;
import samuraisword.game.Game;
import samuraisword.player.Player;
import samuraisword.player.Rol;
import samuraisword.storage.StorageService;

@Controller
public class ProfileController {
	
	private UserService userService;
	
	private StorageService storageService;
	
	public ProfileController(UserService userService, StorageService storageService) {
		this.userService = userService;
		this.storageService = storageService;
	}
	
	@GetMapping(value = "users/myprofile")
	public String viewMyProfile() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "redirect:/users/profile/" + userDetails.getUsername();
	}

	@GetMapping(value = "users/profile/{usernameProfile}")
	public String viewProfile(@PathVariable("usernameProfile") String usernameProfile, Map<String, Object> model) {
		Optional<User> userOptional = userService.findUser(usernameProfile);
		if (userOptional.isEmpty()) {
			return "exception";
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Collection<String>listFriend = userService.getAllFriendOf(usernameProfile);
			User user = userService.findUser(userDetails.getUsername()).get();
			Boolean b=null;
			if(listFriend.contains(user.getUsername())) {				
				b=true;
			}else {
				b=false;
			}
			model.put("b", b);
			model.put("userProfile", userOptional.get());
			model.put("friends", listFriend);
			model.put("username", userDetails.getUsername());
			return "users/profile";
		}
	}

	@GetMapping(value = "users/profile/edit/{usernameProfile}")
	public String viewEditProfile(@PathVariable("usernameProfile") String usernameProfile, Map<String, Object> model) {
		Optional<User> userOptional = userService.findUser(usernameProfile);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userOptional.isEmpty() || !userOptional.get().getUsername().equals(userDetails.getUsername())) {
			return "exception";
		} else {
			model.put("user", userOptional.get());
			return "users/editProfile";
		}
	}

	@PostMapping(value = "users/profile/edit")
	public String saveEditProfile(@Valid User user, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("user", user);
			return "users/editProfile";
		} else {
			userService.saveUser(user);
			return "redirect:/users/profile/" + user.getUsername();
		}
	}
	
	@GetMapping(value ="users/statistics/{usernameProfile}")
	public String viewStatistics(@PathVariable("usernameProfile") String usernameProfile, Map<String, Object> model) {
		Optional<User> userOpt = userService.findUser(usernameProfile);
		if(userOpt.isEmpty()) {
			return "exception";
		} else {
			putStatistics(userOpt.get(), model);
			return "users/statistics";
		}
	}
	
	private void putStatistics(User user, Map<String, Object> model) {
		List<Player> listPlayer = user.getListPlayers();
		Integer playedGames = listPlayer.size();
		model.put("playedGames", playedGames);
		Integer wonGames = (int) listPlayer.stream().filter(player -> player.getGame().getWonPlayers().contains(player.getUser())).count();
		model.put("wonGames", wonGames);
		Integer lostGames = (int) listPlayer.stream().filter(player -> !player.getGame().getWonPlayers().contains(player.getUser())).count();
		model.put("lostGames", lostGames);
		Integer shogunGames = (int) listPlayer.stream().filter(player -> player.getRol().equals(Rol.SHOGUN)).count();
		model.put("shogunGames", shogunGames);
		Integer samuraiGames = (int) listPlayer.stream().filter(player -> player.getRol().equals(Rol.SAMURAI)).count();
		model.put("samuraiGames", samuraiGames);
		Integer ninjaGames = (int) listPlayer.stream().filter(player -> player.getRol().equals(Rol.NINJA)).count();
		model.put("ninjaGames", ninjaGames);
		Integer roninGames = (int) listPlayer.stream().filter(player -> player.getRol().equals(Rol.RONIN)).count();
		model.put("roninGames", roninGames);
	}
	
	@GetMapping(value ="users/game-history/{usernameProfile}")
	public String viewGameHistory(@PathVariable("usernameProfile") String usernameProfile, Map<String, Object> model) {
		System.out.println("TEST");
		Optional<User> userOpt = userService.findUser(usernameProfile);
		if(userOpt.isEmpty()) {
			return "exception";
		} else {
			List<Player> listPlayers = userOpt.get().getListPlayers();
			model.put("listPlayers", listPlayers);
			return "users/gameHistory";
		}
	}
	
	@GetMapping(value = "users/profile/changeAvatar/{usernameProfile}")
	public String viewChangeAvatar(@PathVariable("usernameProfile") String usernameProfile, Map<String, Object> model) {
		Optional<User> userOptional = userService.findUser(usernameProfile);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userOptional.isEmpty() || !userOptional.get().getUsername().equals(userDetails.getUsername())) {
			return "exception";
		} else {
			model.put("user", userOptional.get());
			return "users/changeAvatar";
		}
	}

	@PostMapping(value = "users/profile/changeAvatar")
	public String saveChangeAvatar(@RequestParam("avatar") MultipartFile avatar, HttpSession http) {
		String fileName = storageService.store(avatar, "profile", http);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		user.setAvatar(fileName);
		userService.saveUser(user);
		return "redirect:/users/myprofile";
	}
}
