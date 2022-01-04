package samuraisword.character;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import samuraisword.samples.petclinic.user.User;


public interface CharacterRepository extends CrudRepository<Character, Integer> {
	
	Collection<Character> findAll();
	
	
	@Query(value = "SELECT * FROM CHARACTERS WHERE NAME =:name", nativeQuery=true)
	Character findByName(String name);
}
