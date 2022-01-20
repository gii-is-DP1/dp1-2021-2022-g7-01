package samuraisword.game;



import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import samuraisword.samples.petclinic.card.Card;





public interface GameRepository extends CrudRepository<Game, Integer>{
	
	Collection<Game> findAll();
	

}
