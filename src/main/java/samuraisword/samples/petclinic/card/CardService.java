package samuraisword.samples.petclinic.card;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.comment.Comment;

@Service
public class CardService {
	
	private CardRepository cardRepository;

	@Autowired
	public CardService(CardRepository CardRepository) {
		this.cardRepository = CardRepository;
	}
	
	public Collection<Card> findAll() {
		return cardRepository.findAll();
	}
	
	public Optional<Card> findById(int idCard) {
		return cardRepository.findById(idCard);
	}
	@Transactional
	public void saveCard(Card card) {
		cardRepository.save(card);
	}
	
	public void deleteCard(int idCard) {
		cardRepository.deleteById(idCard);
	}
}
