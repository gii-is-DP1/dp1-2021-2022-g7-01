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
public class GeishaEffectTest {

	@Autowired
	protected CharacterService characterService;
	@Autowired
	protected CardService cardService;
	@Autowired
	protected GameService gameService;
	@Autowired
	protected PlayerService playerService;

	@Test
	void geishaWorksWell() {
		Game g = new Game();
		createTestGame(g);
		Player p = g.getListPlayers().get(0);
		p.getEquipment().add(cardService.findByName("armadura").get());p.setDistanceBonus(p.getDistanceBonus()+1);p.setNArmor(p.getNArmor()+1);
		p.getEquipment().add(cardService.findByName("concentracion").get());;p.setWeaponBonus(p.getWeaponBonus()+1);p.setNFocus(p.getNFocus()+1);
		p.getEquipment().add(cardService.findByName("desenvainado rapido").get());;p.setDamageBonus(p.getDamageBonus()+1);p.setNFastDraw(p.getNFastDraw()+1);
		
		int  i = p.getHand().size();
		int j = g.getDiscardPile().size();
		geishaEffect(g, "hand", p);
		assertThat(p.getHand().size()==(i-1)&& g.getDiscardPile().size()==(j+1)).isTrue();
		
		i=p.getNArmor();
		j = g.getDiscardPile().size();
		geishaEffect(g, "armadura", p);
		assertThat(p.getNArmor()==(i-1)&& g.getDiscardPile().size()==(j+1)).isTrue();
		
		i=p.getNFastDraw();
		j = g.getDiscardPile().size();
		geishaEffect(g, "desenvainado rapido", p);
		assertThat(p.getNFastDraw()==(i-1)&& g.getDiscardPile().size()==(j+1)).isTrue();
		
		i=p.getNFocus();
		j = g.getDiscardPile().size();
		geishaEffect(g, "concentracion", p);
		assertThat(p.getNFocus()==(i-1)&& g.getDiscardPile().size()==(j+1)).isTrue();
		
	}
	
	private void geishaEffect(Game game, String cardName, Player p) {
		switch (cardName) {
		case "hand":
			List<Card>lh = p.getHand();
			if(lh.size()>0) {
				game.setError("");
				game.setGamePhase(GamePhase.MAIN);
				int i = 0 + (int)(Math.random() * ((lh.size() - 0)));
				cardService.discard(lh.get(i).getName(), p.getHand(), game.getDiscardPile());
			} else {
				game.setError("No tiene cartas en mano.");
			}
			break;
		case "armadura":
			if(p.getNArmor()>0) {
				if(!(p.getCharacter().getName().equals("Benkei") && p.getDistanceBonus()==1)) {
					game.setError("");
					p.setDistanceBonus(p.getDistanceBonus()-1);
					game.setGamePhase(GamePhase.MAIN);
					p.setNArmor(p.getNArmor()-1);
					cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
				}
			} else {
				game.setError("No tiene ninguna armadura equipada.");
			}
			break;
		case "concentracion":
			if(p.getNFocus()>0) {
				if(!(p.getCharacter().getName().equals("Goemon") && p.getWeaponBonus()==1)) {
					game.setError("");
					p.setWeaponBonus(p.getWeaponBonus()-1);
					game.setGamePhase(GamePhase.MAIN);
					p.setNFocus(p.getNFocus()-1);
					cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
				}
			} else {
				game.setError("No tiene ninguna concentración equipada.");
			}
			break;
		case "desenvainado rapido":
			if(p.getNFastDraw()>0) {
				if(!(p.getCharacter().getName().equals("Musashi") && p.getDamageBonus()==1)) {
					game.setError("");
					p.setDamageBonus(p.getDamageBonus()+1);
					game.setGamePhase(GamePhase.MAIN);
					p.setNFastDraw(p.getNFastDraw()-1);
					cardService.discard(cardName, p.getEquipment(), game.getDiscardPile());
				}
				
			} else {
				game.setError("No tiene ningún desenvainado rápido equipado.");
			}
			break;
		default:
			game.setError("No ha seleccionado correctamente.");
			break;
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
			player.setNArmor(0);player.setNFastDraw(0);player.setNFocus(0);
			playerService.savePlayer(player);
		}

		gameService.asignCards(game.getDeck(), players);
		gameService.processDrawPhase(game);
		game.setGamePhase(GamePhase.MAIN);

		gameService.asignCards(game.getDeck(), players);
		gameService.saveGame(game);

	}

}
