package samuraisword.game;



import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;





public interface GameRepository extends CrudRepository<Game, Integer>{
	
	Collection<Game> findAll();

	@Query(value = "SELECT g FROM Game g WHERE g.gamePhase<>:lobby AND g.endDate IS NULL")
	List<Game> findGamesInProgress(GamePhase lobby);

	@Query(value = "SELECT g FROM Game g WHERE g.endDate IS NOT NULL")
	List<Game> findGamesEnded();
	
	
	

}
