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

import antlr.collections.List;
import samuraisword.achievements.Achievement;
import samuraisword.achievements.AchievementService;
import samuraisword.achievements.AchievementType;
import samuraisword.achievements.RolType;
import samuraisword.character.CharacterService;
import samuraisword.game.Game;
import samuraisword.game.GamePhase;
import samuraisword.character.Character;
import samuraisword.player.Player;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CharacterServiceTests {
	
	@Autowired
	protected CharacterService characterService;
	
	@Test
	void benkeiTest() {
		Player p1 = new Player();
		Game g = new Game();
		g.setGamePhase(GamePhase.ASSIGN);
		p1.setGame(g);
		Character c = characterService.findByName("Benkei");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	@Test
	void chiyomeTest() {
		Player p1 = new Player();
		Game g = new Game();
		g.setGamePhase(GamePhase.MAIN);
		p1.setGame(g);
		Character c = characterService.findByName("Chiyome");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	@Test
	void chiyomeTestAttackPhase() {
		Player p1 = new Player();
		Game g = new Game();
		g.setGamePhase(GamePhase.ATTACK);
		p1.setGame(g);
		Character c = characterService.findByName("Chiyome");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isFalse();
		
	}
	
	@Test
	void goemonTest() {
		Player p1 = new Player();
		Game g = new Game();
		g.setGamePhase(GamePhase.ASSIGN);
		p1.setGame(g);
		Character c = characterService.findByName("Goemon");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	@Test
	void ieyasuTest() {
		Player p1 = new Player();
		Game g = new Game();
		g.setCurrentPlayer(p1);
		g.setGamePhase(GamePhase.DRAW);
		p1.setGame(g);
		Character c = characterService.findByName("Ieyasu");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	@Test
	void ieyasuTestNotMyTurn() {
		Player p1 = new Player();
		Player p2 = new Player();
		Game g = new Game();
		g.setCurrentPlayer(p2);
		g.setGamePhase(GamePhase.DRAW);
		p1.setGame(g);
		Character c = characterService.findByName("Ieyasu");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isFalse();
		
	}
	
	@Test
	void musashiTest() {
		Player p1 = new Player();
		Game g = new Game();
		g.setGamePhase(GamePhase.ASSIGN);
		p1.setGame(g);
		Character c = characterService.findByName("Musashi");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	
}
