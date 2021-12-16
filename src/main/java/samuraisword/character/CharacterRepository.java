package samuraisword.character;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;


public interface CharacterRepository extends CrudRepository<Character, Integer> {
	
	Collection<Character> findAll();
	
}
