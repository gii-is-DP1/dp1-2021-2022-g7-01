package samuraisword.comment;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

	Collection<Comment> findAll();
}
