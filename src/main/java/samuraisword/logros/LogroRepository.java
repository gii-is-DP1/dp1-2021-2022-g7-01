package samuraisword.logros;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;



public interface LogroRepository extends CrudRepository<Logro, Integer> {

	Collection<Logro> findAll();
	

}
