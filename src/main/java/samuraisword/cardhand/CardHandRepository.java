package samuraisword.cardhand;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

public interface CardHandRepository  extends CrudRepository<CardHand, Integer>{
	
	Collection<CardHand> findAll();
	
}
