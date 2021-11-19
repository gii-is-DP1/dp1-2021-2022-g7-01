package samuraisword.samples.petclinic.user;

import java.util.Collection;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;




public interface UserRepository extends  CrudRepository<User, String>{
	
	
	
	@Query("SELECT DISTINCT user FROM User user WHERE user.username LIKE :username%")
	public Collection<User> findByUsername(@Param("username") String username);
	
	
	
}
