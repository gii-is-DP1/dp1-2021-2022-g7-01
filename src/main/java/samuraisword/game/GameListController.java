package samuraisword.game;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
		List<Game> gamesInProgress = gameListService.findGamesInProgress();
		model.put("gamesInProgress", gamesInProgress);
		List<Game> gamesEnded = gameListService.findGamesEnded();
		model.put("gamesEnded", gamesEnded);
		return "game/listGame";
	}
}
