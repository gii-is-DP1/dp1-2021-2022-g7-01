package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.comment.Comment;
import samuraisword.comment.CommentService;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CommentServiceTests {
	
		@Autowired
		protected CommentService commentService;	

		@Test
		void shouldFindCommentById() {
			Optional<Comment> comment = this.commentService.findById(1);
			assertThat(comment.isPresent());
			
			comment = this.commentService.findById(2);
			assertThat(comment.isEmpty()).isTrue();
		}
		
		@Test
		void shouldHaveTitleAndBody() {
			Collection<Comment> comments = this.commentService.findAll();
			for(Comment c : comments) {
				assertThat(c.getTitle().length() > 0 && c.getBody().length() > 0)
				.isTrue();
			}			
		}
		
		@Test
		@Transactional
		void shouldInsertComment() {
			Collection<Comment> comments = this.commentService.findAll();
			int previousCount = comments.size();
			
			Comment comment = new Comment();
			comment.setTitle("Title");
			comment.setBody("This is the body");
			comment.setCreateDate(Date.valueOf("2022-01-01"));
			
			this.commentService.saveComment(comment);
			comments = this.commentService.findAll();
			
			assertThat(comments.size()).isEqualTo(previousCount + 1);
		}
		
		@Test
		@Transactional
		void shouldUpdateComment() {
			Comment comment = this.commentService.findById(1).get();
			String newTitle = "this is a new title";

			comment.setTitle(newTitle);
			this.commentService.saveComment(comment);

			// retrieving new name from database
			comment = this.commentService.findById(1).get();
			assertThat(comment.getTitle()).isEqualTo(newTitle);
		}


}
