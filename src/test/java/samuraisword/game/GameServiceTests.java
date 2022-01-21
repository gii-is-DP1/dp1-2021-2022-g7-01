package samuraisword.game;

import static org.assertj.core.api.Assertions.assertThat;

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
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.card.RedCard;
import samuraisword.samples.petclinic.user.UserService;

	@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
	public class GameServiceTests {
		
		@Autowired
		protected GameService gService;
		@Autowired
		protected UserService uService;
		@Autowired
		protected PlayerService pService;
		@Autowired
		protected CharacterService cService;
		@Autowired
		protected CardService cardService;
		
		@Test
		void shouldFindGameById() {
			
			Optional<Game> g = this.gService.findById(1);
			assertThat(g.isPresent());
			g = this.gService.findById(10);
			assertThat(g.isEmpty()).isTrue();
		}
		
		@Test
		@Transactional
		void shouldDeleteGame() {
			Collection<Game> lPrev=this.gService.findAll();
			Integer iPrev=lPrev.size();
			this.gService.deleteGame(2);
			Collection<Game> lPos=this.gService.findAll();
			Integer iPos=lPos.size();
			assertThat(iPos<iPrev);
		}
		
		@Test
		void asignCharacterAndHeartsTest() {
			Player p1 = new Player();
			Player p2 = new Player();
			Player p3 = new Player();
			Player p4 = new Player();
			List<Player> players = List.of(p1,p2,p3,p4);
			List<Character> characters = (List<Character>) cService.findAll();
			Integer characterSizeBefore = characters.size();
			gService.asignCharacterAndHearts(players, characters);
			Integer characterSizeAfter = characters.size();
			assertThat(players.stream().allMatch(x -> !x.getCharacter().equals(null)));
			assertThat(characterSizeAfter.equals(characterSizeBefore-players.size()));
		}
		
		@Test
		void createDeckTest() {
			List<Card> ls = gService.createDeck(cardService);
			assertThat(ls.size()==90);
		}
		
		@Test
		void substractHeartsTest() {
			RedCard attackWeapon = cardService.findRedCardByName("bo").get();//bo tiene 1 de ataque
			Player p1 = new Player();
			Player p2 = new Player();
			p1.setCurrentHearts(2);
			p1.setHonor(3);
			gService.substractHearts(p2, p1, attackWeapon);
			assertThat(p1.getCurrentHearts()==2-attackWeapon.getDamage());
			assertThat(p1.getHonor()==3);//no deberia cambiar ya que currentHearts > 0
			
			
			p2.setCurrentHearts(1);
			p2.setHonor(1);
			gService.substractHearts(p1, p2, attackWeapon);
			assertThat(p2.getCurrentHearts()==0 && p2.getHonor()==0 && p2.isDisabled());
		}
		
		@Test
		void handleAttackTest() {
			RedCard attackWeapon = cardService.findRedCardByName("bo").get();//bo tiene 1 de ataque
			
			Card c1 = new Card();
			c1.setName("parada");
						
			Player p1 = new Player();
			p1.setHand(List.of(c1));
			p1.setCurrentHearts(1);
			Player p2 = new Player();
			gService.handleAttack(p2, p1, attackWeapon);
			assertThat(p1.getCurrentHearts()==1);//no se le hace daño ya que tiene una carta de parada

		}

}
