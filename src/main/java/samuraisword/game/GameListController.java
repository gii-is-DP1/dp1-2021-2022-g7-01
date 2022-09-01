package samuraisword.game;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameListController {

	private final GameListService gameListService;
	
	@Autowired
	public GameListController(GameListService gameListService) {
		this.gameListService = gameListService;
	}
	
	@GetMapping(value = "/game/list")
	public String gameList(Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<Game> gamesInProgress = gameListService.findGamesInProgress();
		model.put("gamesInProgress", gamesInProgress);
		List<Game> gamesEnded = gameListService.findGamesEnded();
		model.put("gamesEnded", gamesEnded);
		model.put("authority", userDetails.getAuthorities().toString().contains("admin"));
		return "game/listGame";
	}
}
