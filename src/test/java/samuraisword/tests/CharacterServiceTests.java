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
		System.out.println(c.getGamePhase());
		p1.setCharacter(c);
		characterService.execute(p1);

		assertThat(p1.getDistanceBonus()==1).isTrue();
		
	}
	
	
	
	
	
	
}
