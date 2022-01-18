package samuraisword.samples.petclinic.card;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface CardRepository extends CrudRepository<Card, Integer> {

	Collection<Card> findAll();
	
	@Query(value = "SELECT card FROM Cards WHERE name = :name" , nativeQuery = true)
	Optional<Card> findByName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM Cards WHERE name = :name", nativeQuery = true)
	Optional<RedCard> findRedCardByName(@Param("name") String name);
	
	@Query(value = "SELECT card_color FROM Cards WHERE name = :name" , nativeQuery = true)
	Optional<String> findColor(@Param("name") String name);
	
	@Query(value = "SELECT range FROM Cards WHERE name = :name" , nativeQuery = true)
	Optional<Integer> findRange(@Param("name") String name);
	
	@Query(value = "SELECT damage FROM Cards WHERE name = :name" , nativeQuery = true)
	Optional<Integer> findDamage(@Param("name") String name);
	
	
}
