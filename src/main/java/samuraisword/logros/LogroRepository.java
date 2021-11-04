package samuraisword.logros;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;





public interface LogroRepository extends CrudRepository<Logro, Integer> {

	Collection<Logro> findAll();
	
	@Query("SELECT ltype FROM LogroType ltype ORDER BY ltype.name")
	List<LogroType> findLogroTypes() throws DataAccessException;
	
	@Query("SELECT rtype FROM RolType rtype ORDER BY rtype.name")
	List<RolType> findRolTypes() throws DataAccessException;
}
