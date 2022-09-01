package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import samuraisword.game.Game;
import samuraisword.game.GamePhase;
import samuraisword.game.GameService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.CardService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DaimioTest {
	
	@Autowired
	protected CardService cardService;

	@Autowired
	protected PlayerService playerService;
	
	@Autowired
	protected GameService gameService;
	
	
	@Test
	void checkDaimio() {

		 Game g = new Game();
		 createTestGame(g);
		
		
		Player myPlayer= g.getCurrentPlayer();
		Integer handBefore = myPlayer.getHand().size();
		
		gameService.proceesDrawPhasePlayer(g, myPlayer, 2);
		assertThat(handBefore==(myPlayer.getHand().size()-2)).isTrue();
	}
	
	
	
	
	private void createTestGame(Game game) {
		game.setDeck(cardService.createDeck());
		game.setDiscardPile(new ArrayList<>());
		game.setGamePhase(GamePhase.MAIN);
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
		gameService.saveGame(game);
	}

}
