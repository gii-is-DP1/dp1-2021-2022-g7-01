package samuraisword.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.configuration.SecurityConfiguration;
import samuraisword.samples.petclinic.user.UserService;

@WebMvcTest(value = CardHandController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class ControllerCardHandTest {
	
	@MockBean
	private final CardHandRepository cardHandRepository = null;
	
	@MockBean
	private final PlayerService playerService = null;
	
	@MockBean
	private final CardHandService cardHandService = null;
	
	@MockBean
	private final CardService cardService = null;
	
	@MockBean
	private final UserService userService = null;
	
    @Autowired
	private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
	void testCardHand() throws Exception {
		mockMvc.perform(get("/cardHands"))
		.andExpect(status().isOk())
		.andExpect(view().name("cardHands/listCards"));
	}
    
    @WithMockUser(value = "spring")
    @Test
	void testCardHandNew() throws Exception {
		mockMvc.perform(get("/cardHands/new"))
		.andExpect(status().isOk());
	}
    
//    @WithMockUser(value = "spring")
//    @Test
//	void testCardHandNewPost() throws Exception {
//		mockMvc.perform(post("/cardHands/new")
//				.with(csrf())
//				.param("card_hand_id", "0"))
//		.andExpect(status().isOk());
//	}
    
}
