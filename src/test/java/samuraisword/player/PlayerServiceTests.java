package samuraisword.player;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import samuraisword.game.Game;
import samuraisword.game.GameService;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;
import samuraisword.samples.petclinic.util.EntityUtils;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {

	@Autowired
	protected PlayerService playerService;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected GameService gameService;
	


	
	@Test
	void shouldInsertPlayerProperly() {
		Collection<Player> players = this.playerService.findAll();
		Integer playerSize = players.size();
		
		Player player = new Player();
		User user = this.userService.findUser("diecrequi").get();
		Game game = this.gameService.findById(1).get();
		player.setGame(game);
		player.setUser(user);
		this.playerService.savePlayer(player);
		
		players = this.playerService.findAll();
		assertThat(players.size()).isEqualTo(playerSize + 1);
		assertThat(player.getId()).isNotNull();
	}
	
	@Test
	void shouldDeletePlayerProperly() {
		Collection<Player> players = this.playerService.findAll();
		Integer playerSize = players.size();
		
		Player player = this.playerService.findById(1).get();
		this.playerService.deletePlayer(player.getId());
		
		players = this.playerService.findAll();
		assertThat(players.size()).isEqualTo(playerSize - 1);
		
	}
}
