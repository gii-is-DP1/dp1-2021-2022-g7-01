package samuraisword.achievements;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import samuraisword.comment.Comment;
import samuraisword.samples.petclinic.user.User;





public interface AchievementRepository extends CrudRepository<Achievement, Integer> {

	Collection<Achievement> findAll();
	
	@Query("SELECT ltype FROM AchievementType ltype ORDER BY ltype.name")
	List<AchievementType> findAchievementTypes() throws DataAccessException;
	
	@Query("SELECT rtype FROM RolType rtype ORDER BY rtype.name")
	List<RolType> findRolTypes() throws DataAccessException;
	
}
