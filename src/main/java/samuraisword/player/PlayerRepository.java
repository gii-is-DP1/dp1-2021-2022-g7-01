package samuraisword.player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import samuraisword.achievements.RolType;
import samuraisword.invitations.Invitation;
import samuraisword.samples.petclinic.user.User;


public interface PlayerRepository extends CrudRepository<Player, Integer> {
	
	Collection<Player> findAll();
	
	@Query("SELECT rtype FROM RolType rtype ORDER BY rtype.name")
	List<RolType> findRolTypes() throws DataAccessException;
	
<<<<<<< HEAD
	@Query(value= "SELECT * FROM PLAYERS WHERE USERNAME=:username", nativeQuery=true)
	Optional<Player> findByUser(User username);
=======
//	@Query("SELECT * FROM PLAYERS WHERE USERNAME=:username")
//	Collection<Player> findByUser(String username);
	
	@Query(value="SELECT * FROM PLAYERS WHERE USERNAME=:username", nativeQuery = true)
	Player findByUser(String username);
>>>>>>> master
}
