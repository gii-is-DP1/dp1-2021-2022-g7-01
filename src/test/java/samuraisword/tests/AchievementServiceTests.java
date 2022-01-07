package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.achievements.Achievement;
import samuraisword.achievements.AchievementService;
import samuraisword.achievements.AchievementType;
import samuraisword.achievements.RolType;
import samuraisword.comment.Comment;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTests {
	
	@Autowired
	protected AchievementService achievementService;
	
	@Autowired
	protected UserService userService;
	
	@Test
	void shouldFindAchievementById() {
		Optional<Achievement> achievement = this.achievementService.findById(1);
		assertThat(achievement.isPresent());
		
		achievement = this.achievementService.findById(999);
		assertThat(achievement.isEmpty()).isTrue();
	}
	
	@Test
	void shouldHaveTitleAndBody() {
		Collection<Achievement> achievements = this.achievementService.findAll();
		for(Achievement l : achievements) {
			assertThat(l.getTitle().length() > 0 && l.getBody().length() > 0)
			.isTrue();
		}			
	}
	
	@Test
	@Transactional
	void shouldInsertComment() {
		Collection<Achievement> achievements = this.achievementService.findAll();
		int previousCount = achievements.size();
		
		Achievement achievement = new Achievement();
		
		achievement.setTitle("Title");
		achievement.setBody("This is the body");
		
		this.achievementService.saveAchievements(achievement);
		achievements = this.achievementService.findAll();
		
		assertThat(achievements.size()).isEqualTo(previousCount + 1);
	}
	
	@Test
	@Transactional
	void shouldUpdateAchievement() {
		Achievement achievement = this.achievementService.findById(1).get();
		String newTitle = "this is a new title";

		achievement.setTitle(newTitle);
		this.achievementService.saveAchievements(achievement);

		// retrieving new name from database
		achievement = this.achievementService.findById(1).get();
		assertThat(achievement.getTitle()).isEqualTo(newTitle);
	}
	
	@Test
	@Transactional
	void shouldDeleteAchievement() {
		int count = this.achievementService.findAll().size();
		
		assertThat(this.achievementService.findById(1).isPresent());
		this.achievementService.deleteAchievement(1);
		assertThat(this.achievementService.findAll().size()).isEqualTo(count - 1);		
	}
	
	@Test
	void shouldHaveValidType() {
		Collection<AchievementType> achievementTypes = this.achievementService.findAchievementTypes();
		Optional<Achievement> achievement = this.achievementService.findById(1);
		assertThat(achievement.get().getType()).isIn(achievementTypes);
	}
	
	@Test
	void shouldHaveValidRol() {
		Collection<RolType> rolTypes = this.achievementService.findRolTypes();
		Optional<Achievement> achievement = this.achievementService.findById(1);
		assertThat(achievement.get().getTypes()).isIn(rolTypes);
	}
	
	@Test
	@Transactional
	void shouldActualitateAchievementHelloWorld() {
		User u1 = userService.findUser("admin1").get();
		
		int n1 = achievementService.findAllPersonalAchievements().size();
		achievementService.achivedCheck(u1);	//checks how many completed achievements has "admin1", it should be 1 because "admin1" already
												//wrote a comment, so he should have the achievement "hello world"
		int n2 = achievementService.findAllPersonalAchievements().size();
		assertThat(n2>n1).isTrue();
	}
	
	@Test
	@Transactional
	void shouldActualitateAchievementStalker1() {
		User u1 = userService.findUser("admin1").get();
		
		User u2 = userService.findUser("alefr99").get();
		User u3 = userService.findUser("alfcadmor").get();
		User u4 = userService.findUser("pedolirod").get();
		User u5 = userService.findUser("antquiher1").get();
		User u6 = userService.findUser("juanlo").get();
		
		userService.sendRequested(u1.getUsername(),u2.getUsername());
		userService.sendRequested(u1.getUsername(),u3.getUsername());
		userService.sendRequested(u1.getUsername(),u4.getUsername());
		userService.sendRequested(u1.getUsername(),u5.getUsername());
		userService.sendRequested(u1.getUsername(),u6.getUsername());
		
		int n1 = achievementService.findAllPersonalAchievements().size();
		achievementService.achivedCheck(u1);
		int n2 = achievementService.findAllPersonalAchievements().size();
		assertThat(n2>n1).isTrue();
	}
	
	@Test
	@Transactional
	void shouldActualitateAchievementStalker2() {
		User u1 = userService.findUser("admin1").get();
		
		User u2 = userService.findUser("alefr99").get();
		User u3 = userService.findUser("alfcadmor").get();
		User u4 = userService.findUser("pedolirod").get();
		User u5 = userService.findUser("antquiher1").get();
		User u6 = userService.findUser("juanlo").get();
		
		userService.sendRequested(u1.getUsername(),u2.getUsername());
		userService.sendRequested(u1.getUsername(),u3.getUsername());
		userService.sendRequested(u1.getUsername(),u4.getUsername());
		userService.sendRequested(u1.getUsername(),u5.getUsername());
		userService.sendRequested(u1.getUsername(),u6.getUsername());
		
		userService.acceptRequest(u1.getUsername(),u2.getUsername());
		
		int n1 = achievementService.findAllPersonalAchievements().size();
		achievementService.achivedCheck(u1);	//this checks if someone accepts your friend request before you check your achievement page
		int n2 = achievementService.findAllPersonalAchievements().size();
		assertThat(n2>n1).isTrue();
	}
	
	@Test
	@Transactional
	void shouldActualitateAchievementFriendsWithBenefits() {
		User u1 = userService.findUser("admin1").get();
		
		User u2 = userService.findUser("alefr99").get();
		User u3 = userService.findUser("alfcadmor").get();
		User u4 = userService.findUser("pedolirod").get();
		
		userService.sendRequested(u1.getUsername(),u2.getUsername());
		userService.sendRequested(u1.getUsername(),u3.getUsername());
		userService.sendRequested(u1.getUsername(),u4.getUsername());
		
		userService.acceptRequest(u1.getUsername(),u2.getUsername());
		userService.acceptRequest(u1.getUsername(),u3.getUsername());
		userService.acceptRequest(u1.getUsername(),u4.getUsername());
		
		int n1 = achievementService.findAllPersonalAchievements().size();
		achievementService.achivedCheck(u1);
		int n2 = achievementService.findAllPersonalAchievements().size();
		assertThat(n2>n1).isTrue();
	}
	
	
}
