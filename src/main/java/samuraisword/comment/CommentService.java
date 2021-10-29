package samuraisword.comment;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;

	@Autowired
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public Collection<Comment> findAll() {
		return commentRepository.findAll();
	}
	
	@Transactional
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public Optional<Comment> findById(int idComment) {
		return commentRepository.findById(idComment);
	}
	
	public void deleteComment(int idComment) {
		commentRepository.deleteById(idComment);
	}
}
