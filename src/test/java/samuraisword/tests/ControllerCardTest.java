package samuraisword.tests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.CardController;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.configuration.SecurityConfiguration;
import samuraisword.samples.petclinic.user.UserService;

@WebMvcTest(value = CardController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class ControllerCardTest {
	
	@MockBean
	private final CardService cardService = null;
	
	@MockBean
	private final UserService userService = null;
	
    @Autowired
	private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
	void testListCard() throws Exception {
    	
		mockMvc.perform(get("/cards"))
		.andExpect(status().isOk())
		.andExpect(view().name("cards/cardsList"));
	}
    
    @WithMockUser(value = "spring")
    @Test
	void testCardNewView() throws Exception {
    	
		mockMvc.perform(get("/cards/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("cards/formCard"));
	}
    
//    @Test
//	void testCardNew() throws Exception {
//    	
//    	mockMvc.perform(post("/cards/new").with(csrf())
//    			.param("name", "Tessen")
//    			.param("image", "attack/Tessen")
//    			.param("color", "Red")
//    			.param("cardsPerDeck", "6"))
//    	.andExpect(status().is3xxRedirection())
//    	.andExpect(view().name("redirect:/cards"));
//	}
//   
//    @WithMockUser(value = "spring")
//    @Test
//	void testCardEditView() throws Exception {
//    	
//		mockMvc.perform(get("/cards/edit/{id_card}", 1))
//		.andExpect(status().isOk())
//		.andExpect(view().name("cards/formCard"));
//	}
    
    @WithMockUser(value = "spring")
    @Test
	void testCardDeleteView() throws Exception {
  	
		mockMvc.perform(get("/cards/delete/{id_card}", 1))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/cards"));
	}
 
}
