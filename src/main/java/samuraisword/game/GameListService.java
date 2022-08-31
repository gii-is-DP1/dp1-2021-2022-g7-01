package samuraisword.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameListService {

	private final GameRepository repository;
	
	@Autowired
	public GameListService(GameRepository repository) {
		this.repository = repository;
	}

	public List<Game> findGamesInProgress() {
		return repository.findGamesInProgress(GamePhase.LOBBY);
	}

	public List<Game> findGamesEnded() {
		return repository.findGamesEnded();
	}
}
