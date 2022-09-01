package samuraisword.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import samuraisword.achievements.Achievement;
import samuraisword.achievements.AchievementController;
import samuraisword.achievements.AchievementRepository;
import samuraisword.achievements.AchievementService;
import samuraisword.samples.petclinic.configuration.SecurityConfiguration;
import samuraisword.samples.petclinic.user.UserService;

@WebMvcTest(value = AchievementController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class ControllerAchievementTest {
	
	private static final int TEST_ACHIEVEMENT_ID = 9;
	
	@MockBean
	private final AchievementRepository achievementRepository = null;
	
	@MockBean
    private final AchievementService achievementService = null;
    
	@MockBean
    private final UserService userService = null;
    
    @Autowired
	private MockMvc mockMvc;
    
    @BeforeEach
	void setup() {
    	Optional<Achievement>a = Optional.of(new Achievement());
    	a.get().setId(9);;
    	a.get().setTitle("new achievement");
    	a.get().setBody("Write 1 comment");
    	given(this.achievementService.findById(TEST_ACHIEVEMENT_ID)).willReturn(a);
    }
	
    
//    @WithMockUser(username = "spring", roles = {"USER","ADMIN"})
//    @Test
//	void testCreationPostAchievement() throws Exception {
//    	mockMvc.perform(post("/achievements/new").with(csrf())
//    			.param("title", "Logro titulo")
//    			.param("body", "Write 1 comment")
//    			.param("typeL", "1")
//    			.param("typeR", "1"))
//    	.andExpect(status().is3xxRedirection())
//    	.andExpect(view().name("redirect:/achievements/manage"));
//	}
    
    @WithMockUser(username = "spring", roles = {"USER","ADMIN"})
    @Test
	void testManageAchievement() throws Exception {
		mockMvc.perform(get("/achievements/manage"))
		.andExpect(status().isOk())
		.andExpect(view().name("achievements/achievementManage"));
	}
    
    
    
    
    @WithMockUser(username = "spring", roles = {"USER","ADMIN"})
    @Test
	void testDeleteAchievement() throws Exception {
		mockMvc.perform(get("/achievements/delete/{id_achievement}", TEST_ACHIEVEMENT_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/achievements/manage"));
	}
    
}
