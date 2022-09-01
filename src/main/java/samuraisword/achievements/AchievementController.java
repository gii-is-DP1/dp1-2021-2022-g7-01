package samuraisword.achievements;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

@Controller
public class AchievementController {

	private static final String FORM_LOGRO = "achievements/formAchievement";

	private final AchievementService achievementService;
	private final UserService userService;

	@Autowired
	public AchievementController(AchievementService achievementService, UserService userService) {
		this.achievementService = achievementService;
		this.userService = userService;
	}

	@GetMapping(value = { "/achievements" })
	public String listAchievements(Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		Collection<Achievement> listAchievements = achievementService.findAll();
		achievementService.achivedCheck(user);
		Map<Achievement, Integer>mapAchievement = achievementService.findAllPersonalAchievements(user);
		model.put("listAchievements", listAchievements);
		model.put("mapPersonal", mapAchievement);
		model.put("username", userDetails.getUsername());
		model.put("authority", userDetails.getAuthorities().toString().contains("admin"));
		
		return "achievements/listAchievements";
	}
	
	@GetMapping(value = { "/achievements/manage" })
	public String achievementManage(Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Achievement> listAchievements = achievementService.findAll();
		model.put("listAchievements", listAchievements);
		model.put("username", userDetails.getUsername());
		
		return "achievements/achievementManage";
	}

	
	@ModelAttribute("types1")
	public Collection<AchievementType> populateachievementTypes() {
		return this.achievementService.findAchievementTypes();
	}
	
	@ModelAttribute("types2")
	public Collection<RolType> populateRolTypes() {
		return this.achievementService.findRolTypes();
	}
	
	@GetMapping(value = { "/achievements/delete/{id_achievement}" })
	public String deleteAchievementsForm(@PathVariable("id_achievement") int idAchievement, Map<String, Object> model) {
		achievementService.deleteAchievement(idAchievement);
		return "redirect:/achievements/manage";
	}
}
