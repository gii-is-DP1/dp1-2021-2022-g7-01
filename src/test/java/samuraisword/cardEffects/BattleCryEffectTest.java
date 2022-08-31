package samuraisword.cardEffects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
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
public class BattleCryEffectTest {

	@Autowired
	protected CharacterService characterService;
	@Autowired
	protected CardService cardService;
	@Autowired
	protected GameService gameService;
	@Autowired
	protected PlayerService playerService;

	@Test
	void distractionWorksWell() {
		Game g = new Game();
		createTestGame(g);
		Optional<Card> parada = cardService.findByName("parada");

		for (Player p : g.getListPlayers()) {

			int i = p.getHand().size();
			int j = g.getDiscardPile().size();
			int k = p.getCurrentHearts();
			if (p.getHand().contains(parada.get())) {
				for (Card c : p.getHand()) {
					 i = p.getHand().size();
					 j = g.getDiscardPile().size();
					 k = p.getCurrentHearts();
					if (c.equals(parada)) {
						Random random = new Random();
						int usaParada = random.nextInt(2);

						if (usaParada == 1) {
							cardService.discard(c.getName(), p.getHand(), g.getDiscardPile());
							assertThat(p.getHand().size() == i - 1 && g.getDiscardPile().size() == j + 1
									&& p.getCurrentHearts() == k);
						} else {
							p.setCurrentHearts(p.getCurrentHearts()-1);
							assertThat(p.getHand().size() == i && g.getDiscardPile().size() == j
									&& p.getCurrentHearts() == k-1);
						}
					} else {
						p.setCurrentHearts(p.getCurrentHearts()-1);
						assertThat(p.getHand().size() == i && g.getDiscardPile().size() == j
								&& p.getCurrentHearts() == k-1);
					}
				}
			}else {
				p.setCurrentHearts(p.getCurrentHearts()-1);
				assertThat(p.getHand().size() == i && g.getDiscardPile().size() == j
						&& p.getCurrentHearts() == k-1);
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
		game.getCurrentPlayer().setWeaponBonus(1);

		for (Player player : game.getListPlayers()) {
			player.setGame(game);
			player.setEquipment(new ArrayList<>());
			player.setNArmor(0);
			player.setNFastDraw(0);
			player.setNFocus(0);
			playerService.savePlayer(player);
		}

		gameService.asignCards(game.getDeck(), players);
		gameService.processDrawPhase(game);
		game.setGamePhase(GamePhase.MAIN);

		gameService.asignCards(game.getDeck(), players);
		gameService.saveGame(game);

	}

}
