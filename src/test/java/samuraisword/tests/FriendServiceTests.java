package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.achievements.Achievement;
import samuraisword.achievements.AchievementService;
import samuraisword.achievements.AchievementType;
import samuraisword.achievements.RolType;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class FriendServiceTests {
	
	@Autowired
	protected UserService userService;
	
	@Test
	void shouldFindListfriendByname() {
		Collection<String> user = this.userService.getAllFriendOf("diecrequi");
		assertThat(!user.isEmpty());
		
		user = this.userService.getAllFriendOf("alguien");
		assertThat(user.isEmpty()).isTrue();
	}
	
	@Test
	void shouldFindListrequestedByname() {
		Collection<String> user = this.userService.listRequestAll("diecrequi");
		assertThat(!user.isEmpty());
		
		user = this.userService.listRequestAll("alguien");
		assertThat(user.isEmpty()).isTrue();
	}
	
	@Test
	@Transactional
	void shouldSendRequestedFriend() {
		Collection<String> antiListReq = this.userService.listRequestAll("antquiher1");
		int previousCount = antiListReq.size();
		this.userService.sendRequested("admin1", "antquiher1");
		Collection<String> ListReq = this.userService.listRequestAll("antquiher1");
		int count = ListReq.size();
		assertThat(count!=previousCount);
		
		previousCount = antiListReq.size();
		this.userService.sendRequested("admin1", "antquiher1");
		ListReq = this.userService.listRequestAll("antquiher1");
		count = ListReq.size();
		assertThat(count==previousCount).isTrue();
	}
	
	@Test
	@Transactional
	void shouldacceptRequestFriend() {
		Collection<String> antiListReq = this.userService.listRequestAll("diecrequi");
		int previousCountReq = antiListReq.size();
		Collection<String> antiUserFrie = this.userService.getAllFriendOf("diecrequi");
		int previousCountfri = antiUserFrie.size();
		
		this.userService.acceptRequest("diecrequi", "alefr99");
		
		Collection<String> ListReq = this.userService.listRequestAll("diecrequi");
		int countReq = ListReq.size();
		Collection<String> userFrie = this.userService.getAllFriendOf("diecrequi");
		int countFri = userFrie.size();
		
		assertThat(countReq!=previousCountReq || countFri!=previousCountfri);
		
		antiListReq = this.userService.listRequestAll("pedolirod");
		previousCountReq = antiListReq.size();
		antiUserFrie = this.userService.getAllFriendOf("pedolirod");
		previousCountfri = antiUserFrie.size();
	}

	@Test
	@Transactional
	void shouldDeclineRequestFriend() {
		Collection<String> antiListReq = this.userService.listRequestAll("diecrequi");
		int previousCountReq = antiListReq.size();
		Collection<String> antiUserFrie = this.userService.getAllFriendOf("diecrequi");
		int previousCountfri = antiUserFrie.size();
		
		this.userService.declineRequest("diecrequi", "alefr99");
		
		Collection<String> ListReq = this.userService.listRequestAll("diecrequi");
		int countReq = ListReq.size();
		Collection<String> userFrie = this.userService.getAllFriendOf("diecrequi");
		int countFri = userFrie.size();
		
		assertThat(countReq!=previousCountReq || countFri!=previousCountfri);
	}
	
	
	
	
}
