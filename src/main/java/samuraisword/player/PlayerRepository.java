package samuraisword.player;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import samuraisword.achievements.RolType;


public interface PlayerRepository extends CrudRepository<Player, Integer> {
	
	Collection<Player> findAll();
	@Query("SELECT rtype FROM RolType rtype ORDER BY rtype.name")
	List<RolType> findRolTypes() throws DataAccessException;
}
