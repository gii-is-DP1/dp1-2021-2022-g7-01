package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Optional;
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

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTests {
	
	@Autowired
	protected AchievementService achievementService;
	
	@Test
	void shouldFindAchievementById() {
		Optional<Achievement> achievement = this.achievementService.findById(1);
		assertThat(achievement.isPresent());
		
		achievement = this.achievementService.findById(2);
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
	
	
	
	
	
	
}
