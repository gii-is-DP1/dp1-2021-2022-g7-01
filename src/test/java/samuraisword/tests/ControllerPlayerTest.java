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
import samuraisword.game.GameService;
import samuraisword.player.PlayerController;
import samuraisword.player.PlayerRepository;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.configuration.SecurityConfiguration;
import samuraisword.samples.petclinic.user.UserService;

@WebMvcTest(value = PlayerController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class ControllerPlayerTest {
	
	@MockBean
	private final PlayerRepository playerRepository = null;
	
	@MockBean
	private final PlayerService playerService = null;
	
	@MockBean
	private final CardService cardService = null;
	
	@MockBean
	private final GameService gameService = null;
	
	
    @Autowired
	private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
	void testPlayerList() throws Exception {
    	
		mockMvc.perform(get("/players"))
		.andExpect(status().isOk())
		.andExpect(view().name("players/listPlayers"));
	}
    
   
    
    @WithMockUser(value = "admin1")
    @Test
	void testDeleteComment() throws Exception {
    	
		mockMvc.perform(get("/game/{id_game}/players/delete/{id_player}", 1, 1))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/game/{id_game}"));
	}
    
}
