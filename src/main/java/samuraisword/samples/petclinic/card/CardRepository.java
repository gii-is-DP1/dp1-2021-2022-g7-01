package samuraisword.samples.petclinic.card;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Integer> {

	Collection<Card> findAll();
	
	@Query(value = "SELECT card FROM Cards WHERE name = :name" , nativeQuery = true)
	Optional<Card> findByName(@Param("name") String name);
}
