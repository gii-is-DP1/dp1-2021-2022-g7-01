package samuraisword.tests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import samuraisword.cardhand.CardHandController;
import samuraisword.cardhand.CardHandRepository;
import samuraisword.cardhand.CardHandService;
import samuraisword.comment.CommentController;
import samuraisword.comment.CommentService;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.configuration.SecurityConfiguration;
import samuraisword.samples.petclinic.user.UserService;

@WebMvcTest(value = CommentController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class ControllerCommentTest {
	
	@MockBean
	private final CommentService commentService = null;
	
	@MockBean
	private final UserService userService = null;
	
	
    @Autowired
	private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
    	
		mockMvc.perform(get("/comments/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("comments/formComment"));
	}
    
    
    
    @WithMockUser(value = "admin1")
    @Test
	void testDeleteComment() throws Exception {
    	
		mockMvc.perform(get("/comments/delete/{id_comment}", 1))
		.andExpect(status().is3xxRedirection())		//el is3xxRedirection es para si modificas la bd
		.andExpect(view().name("redirect:/comments"));
	}
    
    
    
}
