package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
import samuraisword.player.PlayerService;
import samuraisword.player.Rol;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CharacterTests {

	@Autowired
	protected CharacterService characterService;
	@Autowired
	protected CardService cardService;
	@Autowired
	protected GameService gameService;
	@Autowired
	protected PlayerService playerService;

	@Test
	void allDifferentCharacters() {
		Game g = new Game();
		createTestGame(g);

		Set<Character> characters = new HashSet<Character>();
		for (Player p : g.getListPlayers()) {
			characters.add(p.getCharacter());
		}

		assertThat(characters.size() == g.getListPlayers().size());

	}

	@Test
	void checkBenkeiAction() {
		Game g = new Game();
		createTestGame(g);
		for (Player p : g.getListPlayers()) {

			if (p.getCharacter().getName().equals("Benkei")) {
				assertThat(p.getDistanceBonus() == 1).isTrue();

			}
		}

	}

	@Test
	void checkGoemonAction() {
		Game g = new Game();
		createTestGame(g);
		for (Player p : g.getListPlayers()) {

			if (p.getCharacter().getName().equals("Goemon")) {
				assertThat(p.getWeaponBonus() == 1).isTrue();
			}
		}

	}

	@Test
	void checkGinchiyoAction() {
		Game g = new Game();
		createTestGame(g);
		for (Player p : g.getListPlayers()) {

			if (p.getCharacter().getName().equals("Ginchiyo")) {
				assertThat(p.getAntiDamageBonus() == 1).isTrue();
			}
		}

	}

	@Test
	void checkHideyoshiAction() {
		Game g = new Game();
		createTestGame(g);

		for (Player p : g.getListPlayers()) {

			if (p.getCharacter().getName().equals("Hideyoshi")) {
				g.setCurrentPlayer(p);
				int n = p.getHand().size();
				gameService.processDrawPhase(g);
				assertThat(p.getHand().size() == n + 3).isTrue();
			}
		}

	}

	@Test
	void checkMusashiAction() {
		Game g = new Game();
		createTestGame(g);
		for (Player p : g.getListPlayers()) {

			if (p.getCharacter().getName().equals("Musashi")) {
				assertThat(p.getDamageBonus() == 1).isTrue();
			}
		}

	}

	@Test
	void checkChiyomeAction() {
		Game game = new Game();
		createTestGame(game);

		List<Player> lp = new ArrayList<>();
		game.setWaitingForPlayer(lp);
		for (int a = 0; a < game.getListPlayers().size(); a++) {
			if (!(game.getListPlayers().get(a).equals(game.getCurrentPlayer()))) {
				game.getWaitingForPlayer().add(game.getListPlayers().get(a));
			}
			if (game.getListPlayers().get(a).getCharacter().getName().equals("Chiyome")) {
				game.getWaitingForPlayer().remove(game.getListPlayers().get(a));
			}
		}
		for (Player p : game.getWaitingForPlayer()) {
			System.out.println("pepe"+ p.getCharacter().getName());
			assertThat(p.getCharacter().getName().equals("Chiyomee")).isFalse();
		}
	}

	@Test
	void checkTomoeAction() {
		System.out.println("pepe");
		Game g = new Game();
		createTestGame(g);
		int n = 0;
		int m = g.getDeck().size();
		

		for (Player p : g.getListPlayers()) {

			if (p.getCharacter().getName().equals("Tomoe")) {
				n=p.getHand().size();
				Card card = g.getDeck().get(0);
				p.getHand().add(card);
				g.getDeck().remove(0);
			}
		}
		for (Player p : g.getListPlayers()) {

			if (p.getCharacter().getName().equals("Tomoe")) {
				assertThat(p.getHand().size()==(n+1) && g.getDeck().size()==(m-1)).isTrue();
			}
		}

	}

	private void createTestGame(Game game) {
		game.setDeck(cardService.createDeck());
		game.setDiscardPile(new ArrayList<>());

		List<Player> players = game.getListPlayers();

		// players de prueba
		Player p1 = playerService.findById(1).get();

		Player p2 = playerService.findById(2).get();

		Player p3 = playerService.findById(3).get();

		Player p4 = playerService.findById(4).get();

		Player p5 = playerService.findById(5).get();

		Player p6 = playerService.findById(6).get();

		Player p7 = playerService.findById(7).get();

		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		players.add(p5);
		players.add(p6);
		players.add(p7);

		gameService.asignCharacterAndHearts(players);
		gameService.asignRolAndHonor(players);
		gameService.asignOrder(players);

		game.setListPlayers(players);
		game.setCurrentPlayer(players.get(0));

		for (Player player : game.getListPlayers()) {
			player.setGame(game);
			playerService.savePlayer(player);
		}

		gameService.asignCards(game.getDeck(), players);

	}

}
