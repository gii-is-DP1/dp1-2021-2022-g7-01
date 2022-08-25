package samuraisword.game;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.character.Character;
import samuraisword.character.CharacterService;
import samuraisword.game.Game;
import samuraisword.game.GameService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.player.Rol;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.card.RedCard;
import samuraisword.samples.petclinic.user.UserService;

	@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
	public class GameTests {
		
		@Autowired
		protected GameService gameService;
		
		@Autowired
		protected CardService cardService;
		
		@Autowired
		protected PlayerService playerService;
		
		@Test
		void shouldStartShogun() {
			Game g= new Game();
			createTestGame(g);
			
			
			assertThat(g.getListPlayers().get(0).getRol().equals(Rol.SHOGUN));
		}
		
		@Test
		void shouldCharacterHaveCorrectHandNumber() {
			Game g= new Game();
			createTestGame(g);
			
			
			switch (g.getListPlayers().size()) {
			
			case 4:
				assertThat(g.getListPlayers().get(0).getHand().size() == 4
				&& (g.getListPlayers().get(1).getHand().size() ==5 && g.getListPlayers().get(2).getHand().size() == 5)
				&& (g.getListPlayers().get(3).getHand().size() ==6 )
						);
				break;
			case 5:
				assertThat(g.getListPlayers().get(0).getHand().size() == 4
				&& (g.getListPlayers().get(1).getHand().size() ==5 && g.getListPlayers().get(2).getHand().size() == 5)
				&& (g.getListPlayers().get(3).getHand().size() ==6 && g.getListPlayers().get(4).getHand().size() == 6)
						);
				break;
			case 6:
				assertThat(g.getListPlayers().get(0).getHand().size() == 4
				&& (g.getListPlayers().get(1).getHand().size() ==5 && g.getListPlayers().get(2).getHand().size() == 5)
				&& (g.getListPlayers().get(3).getHand().size() ==6 && g.getListPlayers().get(4).getHand().size() == 6)
				&& (g.getListPlayers().get(5).getHand().size() ==4)
						);
				break;
			case 7:
				assertThat(g.getListPlayers().get(0).getHand().size() == 4
				&& (g.getListPlayers().get(1).getHand().size() ==5 && g.getListPlayers().get(2).getHand().size() == 5)
				&& (g.getListPlayers().get(3).getHand().size() ==6 && g.getListPlayers().get(4).getHand().size() == 6)
				&& (g.getListPlayers().get(5).getHand().size() ==4 && g.getListPlayers().get(6).getHand().size() == 5)
						);
				break;
			
			}
		}
		
		@Test
		void shouldCorrectPhaseAfterEndTurn() {
			Game g= new Game();
			createTestGame(g);
			Player p = g.getCurrentPlayer();
			
			gameService.endTurn(g);
			assertThat(g.getGamePhase().equals(GamePhase.RECOVERY) && g.getCurrentPlayer().getId()== p.getId()+1l );
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

			players.add(p1);
			players.add(p2);
			players.add(p3);
			players.add(p4);
			players.add(p5);
			players.add(p6);

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
