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

import samuraisword.achievements.AchievementController;
import samuraisword.achievements.AchievementRepository;
import samuraisword.achievements.AchievementService;
import samuraisword.samples.petclinic.configuration.SecurityConfiguration;
import samuraisword.samples.petclinic.user.UserService;

@WebMvcTest(value = AchievementController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)

//Este test no esta bien implementado. la redireccion esta mal y como tal le faltan detalles
// (no se como crear un usuario vacio de tipo userDetails)

public class ControllerAchievementTest {
    @MockBean
	AchievementService achievementService;
    
    @MockBean
	UserService userService;
    
	@MockBean
	AchievementRepository achievementRepository;
	
    @Autowired
	private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
    	
		mockMvc.perform(get("/achievements").with(csrf())
				.param("user", "admin1")
				.param("comment", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("achievements/listAchievements"));
	}
}
