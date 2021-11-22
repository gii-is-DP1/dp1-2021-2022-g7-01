package samuraisword.game;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.comment.Comment;

@Service
public class GameService {
	
private GameRepository gameRepository;	
	
@Autowired
public GameService(GameRepository gameRepository) {
	this.gameRepository = gameRepository;
}

public Optional<Game> findById(int idGame) {
	return gameRepository.findById(idGame);
}
	
	@Transactional
	public void saveGame(Game game) throws DataAccessException {
		//creating game
		gameRepository.save(game);	

	}	

}
