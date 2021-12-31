package samuraisword.comment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import samuraisword.samples.petclinic.user.User;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

	Collection<Comment> findAll();
	
	@Query(value = "SELECT * FROM COMMENTS WHERE USERNAME =:user", nativeQuery=true)
	Collection<Comment> findByUser(User user);
}
