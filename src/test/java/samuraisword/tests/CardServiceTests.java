package samuraisword.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.achievements.Achievement;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CardServiceTests {
	
	@Autowired
	protected CardService cardService;
	
	@ParameterizedTest
	@ValueSource(ints = {1,2,3,4,5})
	void shouldFindCardById(int id) {		
		Optional<Card> card = this.cardService.findById(id);
		assertThat(card.isPresent());
		
		card = this.cardService.findById(500);
		assertThat(card.isEmpty()).isTrue();
	}
	
	@Test
	void shouldHaveImage() {
		Collection<Card> cards = this.cardService.findAll();
		for(Card c : cards) {
			assertThat(c.getImage().isBlank()).isFalse();
			assertThat(c.getImage()).endsWith(".png");
		}
	}
	
	@Test
	@Transactional
	void shouldInsertCard() {
		Collection<Card> cards = this.cardService.findAll();
		int previousCount = cards.size();
		
		Card card = new Card();
		
		card.setName("prueba");
		card.setImage("attack/bokken.png");
		
		this.cardService.saveCard(card);
		
		cards = this.cardService.findAll();
		
		assertThat(cards.size()).isEqualTo(previousCount + 1);
	}
	
	@Test
	@Transactional
	void shouldUpdateCard() {
		Card card = this.cardService.findById(1).get();
		String newName = "this is a new name";

		card.setName(newName);
		this.cardService.saveCard(card);

		// retrieving new name from database
		card = this.cardService.findById(1).get();
		assertThat(card.getName()).isEqualTo(newName);
	}
	
	@Test
	@Transactional
	void shouldDeleteCard() {
		int count = this.cardService.findAll().size();
		
		assertThat(this.cardService.findById(1).isPresent());
		this.cardService.deleteCard(1);
		assertThat(this.cardService.findAll().size()).isEqualTo(count - 1);		
	}
	
	
	
	
	
	
	
}
