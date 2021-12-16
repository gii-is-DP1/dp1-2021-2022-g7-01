package samuraisword.achievements;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import samuraisword.samples.petclinic.user.User;





public interface PersonalAchievementRepository extends CrudRepository<PersonalAchievement, Integer> {

	//Collection<PersonalAchievement> findAll();
	
	@Query(value = "SELECT TITLE FROM PERSONAL_ACHIEVEMENTS WHERE USERNAME =:user", nativeQuery=true)
	Collection<Integer> findAll(User user);
	
	@Modifying
	@Query(
	  value = 
	    "insert into PERSONAL_ACHIEVEMENTS (TITLE, USERNAME) values (:achievement, :user)",
	  nativeQuery = true)
	void achieved( @Param("achievement") Integer achievement,@Param("user") String user);
	
}
