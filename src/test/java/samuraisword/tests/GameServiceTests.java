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

import samuraisword.game.Game;
import samuraisword.game.GameService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.user.UserService;


	
	@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
	public class GameServiceTests {
		
		@Autowired
		protected GameService gService;
		protected UserService uService;
		protected PlayerService pService;
		
		@Test
		void shouldFindGameById() {
			
			Optional<Game> g = this.gService.findById(1);
			assertThat(g.isPresent());
			
			g = this.gService.findById(10);
			assertThat(g.isEmpty()).isTrue();
		}
		
		@Test
		@Transactional
		void shouldDeleteGame() {
		
			Collection<Game> lPrev=this.gService.findAll();
			Integer iPrev=lPrev.size();
			this.gService.deleteGame(2);
			Collection<Game> lPos=this.gService.findAll();
			Integer iPos=lPos.size();
			assertThat(iPos<iPrev);
			
		}
		
		//Pedro: he movido los tests de statup y statdown a CharacterServiceTests

}
