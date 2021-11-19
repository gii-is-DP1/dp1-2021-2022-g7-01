package samuraisword.samples.petclinic.card;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer> {

	Collection<Card> findAll();
}
