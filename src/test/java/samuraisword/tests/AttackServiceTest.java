package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import samuraisword.game.Game;
import samuraisword.game.GamePhase;
import samuraisword.game.GameService;
import samuraisword.game.GameSingleton;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AttackServiceTest {
	
	@Autowired
	protected CardService cardService;

	@Autowired
	protected PlayerService playerService;
	
	@Autowired
	protected GameService gameService;
	
	
	@Test
	void CheckAttack(){
		Game g = new Game();
		createTestGame(g);
		Player POVplayer = g.getCurrentPlayer();
		List<Card> redCards = cardService.findAllRedCards();
		Collections.shuffle(redCards);
		String cardRandom =redCards.get(0).getName();
		
		List<Player> lp=gameService.playersInRangeOfAttack(g, cardService.findRedCardByName(cardRandom).get(), POVplayer);
		for(int e=0;e<lp.size();e++) {
			if(lp.get(e).getUser().getUsername().equals(POVplayer.getUser().getUsername())) {
				lp.remove(e);
			}
			g.setPlayersInRange(lp);
			
		Card redCardRandom= cardService.findRedCardByName(cardRandom).get();
		g.setUseCard(redCardRandom);
		Card c= g.getUseCard();
		int distancia = cardService.findRange(c.getName()).get();
		
		int random= (int) Math.random()* (distancia -0);
		String playerA= g.getPlayersInRange().get(random).toString();
		
		
		Player p=playerService.findPlayerByUsernameAndGame(playerA, g);
		
		int dano=cardService.findDamage(g.getUseCard().getName()).get();
		if(dano>1) {
			g.setAttackerDamage(dano+g.getCurrentPlayer().getDamageBonus()-p.getAntiDamageBonus());
			}else{
				g.setAttackerDamage(dano+g.getCurrentPlayer().getDamageBonus());
			}
		
		g.setAttackerPlayer(p);
		int i=cardService.findDamage(c.getName()).get()+g.getCurrentPlayer().getDamageBonus();
		for(int i2=0;i2<g.getListPlayers().size();i2++) {
			if(g.getListPlayers().get(i2).getUser().getUsername().equals(playerA)) {
				if(i>1) {
				g.getListPlayers().get(i2).setCurrentHearts(g.getListPlayers().get(i2).getCurrentHearts()-i+g.getListPlayers().get(i2).getAntiDamageBonus());
				assertThat(p.getCurrentHearts()==p.getCurrentHearts()-i+g.getListPlayers().get(i2).getAntiDamageBonus());
				}else{
					g.getListPlayers().get(i2).setCurrentHearts(g.getListPlayers().get(i2).getCurrentHearts()-i);
				assertThat(p.getCurrentHearts()==p.getCurrentHearts()-i);
				}
				if(g.getCurrentPlayer().getCharacter().getName().equals("Tomoe")) {
					Card card = g.getDeck().get(0);
					g.getCurrentPlayer().getHand().add(card);
					g.getDeck().remove(0);
					
				}
				if(g.getListPlayers().get(i2).getCurrentHearts()<=0) {
					g.getListPlayers().get(i2).setIndefence(true);
					if(g.getListPlayers().get(i2).getHonor()>0) {
//						game.getListPlayers().get(i2).setCurrentHearts(game.getListPlayers().get(i2).getCharacter().getLife());
						g.getListPlayers().get(i2).setHonor(g.getListPlayers().get(i2).getHonor()-1);
						g.getCurrentPlayer().setHonor(g.getCurrentPlayer().getHonor()+1);
					}
				}
			}
		}
		g.setGamePhase(GamePhase.AVISO);
		}
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
