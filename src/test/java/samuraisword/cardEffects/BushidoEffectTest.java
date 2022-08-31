package samuraisword.cardEffects;

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
public class BushidoEffectTest {

	@Autowired
	protected CharacterService characterService;
	@Autowired
	protected CardService cardService;
	@Autowired
	protected GameService gameService;
	@Autowired
	protected PlayerService playerService;

	@Test
	void bushidoWorksWell() {
		Game game = new Game();
		createTestGame(game);
		Optional<Card> bush = cardService.findByName("bushido");

		for (Player p : game.getListPlayers()) {
			game.setCurrentPlayer(p);
			
			Card card = game.getDeck().get(0);
			game.getDiscardPile().add(card);
			game.getDeck().remove(0);
			Integer numPlayers = game.getListPlayers().size();
			Integer nextPlayerIndex = (game.getListPlayers().indexOf(game.getCurrentPlayer()) + 1) % numPlayers;
			if (card.getColor().equals("Red")) {
				boolean hasRedCard = false;
				for (int i = 0; i < game.getCurrentPlayer().getHand().size(); i++) {
					if (game.getCurrentPlayer().getHand().get(i).getColor().equals("Red")) {
						hasRedCard = true;
					}
				}
				if (hasRedCard) {
					game.setGamePhase(GamePhase.DISCARTRED);
				} else {
					int n = game.getCurrentPlayer().getHonor();
					game.getCurrentPlayer().setHonor(game.getCurrentPlayer().getHonor() - 1);
					game.getCurrentPlayer().getEquipment().remove(bush.get());
					game.getListPlayers().get(nextPlayerIndex).getEquipment().add(bush.get());
					assertThat(game.getListPlayers().get(nextPlayerIndex).getEquipment().contains(bush.get()));
					assertThat(game.getCurrentPlayer().getHonor()==n-1);
				}
			} else {
				game.getCurrentPlayer().getEquipment().remove(bush.get());
				game.getListPlayers().get(nextPlayerIndex).getEquipment().add(bush.get());
				assertThat(game.getListPlayers().get(nextPlayerIndex).getEquipment().contains(bush.get()));
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
