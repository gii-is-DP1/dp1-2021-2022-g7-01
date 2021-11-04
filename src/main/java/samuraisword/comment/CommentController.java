package samuraisword.comment;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class CommentController {

	private static final String FORM_COMMENT = "comments/formComment";

	private final CommentService commentService;
	private final UserService userService;

	@Autowired
	public CommentController(CommentService commentService, UserService userService) {
		this.commentService = commentService;
		this.userService = userService;
	}

	@GetMapping(value = { "/comments" })
	public String listComments(Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails.getUsername());
		Collection<Comment> listComments = commentService.findAll();
		model.put("listComments", listComments);
		model.put("username", userDetails.getUsername());
		
		return "comments/listComments";
	}

	@GetMapping(value = { "/comments/new" })
	public String newCommentForm(Map<String, Object> model) {
		Comment comment = new Comment();
		model.put("comment", comment);
		return FORM_COMMENT;
	}

	@PostMapping(value = "/comments/new")
	public String processCreationForm(@Valid Comment comment, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("comment", comment);
			return FORM_COMMENT;
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			System.out.println(user);
			comment.setCreateDate(new Date());
			comment.setUser(user);
			commentService.saveComment(comment);
			return "redirect:/comments";
		}
	}
	
	@GetMapping(value = { "/comments/edit/{id_comment}" })
	public String editCommentForm(@PathVariable("id_comment") int idComment, Map<String, Object> model) {
		Comment comment = commentService.findById(idComment).get();
		model.put("comment", comment);
		return FORM_COMMENT;
	}
	
	@PostMapping(value = { "/comments/edit/{id_comment}" })
	public String processEditForm(@PathVariable("id_comment") int idComment, @Valid Comment comment, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("comment", comment);
			return FORM_COMMENT;
		}
		Comment commentToUpdate = commentService.findById(idComment).get();
		BeanUtils.copyProperties(comment, commentToUpdate, "id","user");
		commentService.saveComment(commentToUpdate);
		return "redirect:/comments";
	}
	
	@GetMapping(value = { "/comments/delete/{id_comment}" })
	public String deleteCommentForm(@PathVariable("id_comment") int idComment, Map<String, Object> model) {
		commentService.deleteComment(idComment);
		return "redirect:/comments";
	}
}