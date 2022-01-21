package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
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
import samuraisword.game.GameService;
import samuraisword.character.Character;
import samuraisword.player.Player;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CharacterServiceTests {
	
	@Autowired
	protected CharacterService characterService;
	@Autowired
	protected CardService cardService;
	@Autowired
	protected GameService gameService;
	
	@Test
	void benkeiTest() {
		Player p1 = new Player();
		Game g = new Game();
		p1.setGame(g);
		Character c = characterService.findByName("Benkei");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	@Test
	void chiyomeTest() {
		Player p1 = new Player();
		Game g = new Game();
		p1.setGame(g);
		Character c = characterService.findByName("Chiyome");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	@Test
	void ginchiyoTest() {
		Player p1 = new Player();
		Game g = new Game();
		p1.setGame(g);
		Character c = characterService.findByName("Ginchiyo");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();		
	}
	
	
	@Test
	void hideyoshiTest() {
		Player p1 = new Player();
		Game g = new Game();
		p1.setGame(g);
		Character c = characterService.findByName("Hideyoshi");
		p1.setCharacter(c);
		p1.setHonor(3);

		assertThat(characterService.execute(p1)).isTrue();
	}
	
	@Test
	void goemonTest() {
		Player p1 = new Player();
		Game g = new Game();
		p1.setGame(g);
		Character c = characterService.findByName("Goemon");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	@Test
	void musashiTest() {
		Player p1 = new Player();
		Game g = new Game();
		p1.setGame(g);
		Character c = characterService.findByName("Musashi");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
		
	}
	
	@Test
	void tomoeTest() {
		Player p1 = new Player();
		Game g = new Game();
		p1.setGame(g);
		Character c = characterService.findByName("Tomoe");
		p1.setCharacter(c);

		assertThat(characterService.execute(p1)).isTrue();
	}
	
	
	@Test
	@Transactional
	void shouldStatUp() {
	Player p=new Player();
	Integer iPrev=p.getDamageBonus();
	characterService.statUp(p, "damageBonus", 1);
	Integer iPos=p.getDamageBonus();
	assertThat(iPos>iPrev);
	}
	
	@Test
	@Transactional
	void shouldStatDown() {
	Player p=new Player();
	p.setWeaponBonus(1);
	Integer iPrev=p.getWeaponBonus();
	characterService.statUp(p, "weaponBonus", 1);
	Integer iPos=p.getWeaponBonus();
	assertThat(iPos<iPrev);
	}
}
