package samuraisword.game;



import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import samuraisword.character.Character;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.user.User;





public interface GameRepository extends CrudRepository<Game, Integer>{
	
	Collection<Game> findAll();
	
	
	

}
