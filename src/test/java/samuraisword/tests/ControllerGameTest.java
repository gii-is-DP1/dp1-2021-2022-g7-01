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
import samuraisword.character.CharacterService;
import samuraisword.game.GameController;
import samuraisword.game.GameService;
import samuraisword.invitations.InvitationService;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.configuration.SecurityConfiguration;
import samuraisword.samples.petclinic.user.UserService;

@WebMvcTest(value = GameController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class ControllerGameTest {
	private static final int TEST_GAME_ID = 3;
	
	@MockBean
	private final GameService gameService = null;
	
	@MockBean
	private final PlayerService playerService = null;
	
	@MockBean
	private final UserService userService = null;
	
	@MockBean
	private final CardService cardService = null;
	
	@MockBean
	private final InvitationService invitationService = null;
	
	@MockBean
	private final CharacterService characterService = null;
	
    @Autowired
	private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/game/delete/{id_game}", TEST_GAME_ID))
		.andExpect(view().name("redirect:/game/new"));
	}
}
