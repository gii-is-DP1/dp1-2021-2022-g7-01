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
import samuraisword.game.GameStatus;
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
	void ginchiyoTest() {
		Player p1 = new Player();
		Game g = new Game();
		g.setGamePhase(GamePhase.ATTACK);
		g.setStatus(GameStatus.ATTACK);
		p1.setGame(g);
		Character c = characterService.findByName("Ginchiyo");
		p1.setCharacter(c);
		p1.setCurrentHearts(c.getLife());
		g.setListPlayers(new ArrayList<Player>());
		g.getListPlayers().add(p1);
		
		assertThat(characterService.execute(p1)).isTrue();		
	}
	
	@Test
	void hanzoTest() {
		Player p1 = new Player();
		Game g = new Game();
		g.setGamePhase(GamePhase.ATTACK);
		g.setStatus(GameStatus.ATTACK);
		p1.setGame(g);
		Character c = characterService.findByName("Hanzo");
		Optional<Card> card = cardService.findByName("parada");
		p1.setHand(new ArrayList<Card>());
		p1.getHand().add(card.get());
		p1.setCharacter(c);
		g.setListPlayers(new ArrayList<Player>());
		g.getListPlayers().add(p1);
		
		
		assertThat(characterService.execute(p1)).isTrue();		
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
	void kojiroTest() {
		Player p1 = new Player();
		Game g = new Game();
		g.setCurrentPlayer(p1);
		g.setGamePhase(GamePhase.ATTACK);
		p1.setGame(g);
		Character c = characterService.findByName("Kojiro");
		p1.setHand(new ArrayList<Card>());
		p1.setCharacter(c);
		g.setListPlayers(new ArrayList<Player>());
		g.getListPlayers().add(p1);

		assertThat(characterService.changeStatus(g, GameStatus.ATTACK).contains(p1)).isTrue();
		
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
