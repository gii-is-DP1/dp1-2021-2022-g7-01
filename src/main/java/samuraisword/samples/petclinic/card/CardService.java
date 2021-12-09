package samuraisword.samples.petclinic.card;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.comment.Comment;
import samuraisword.samples.petclinic.user.User;

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
	
	public Optional<Card> findByName(String name){
		return cardRepository.findByName(name);
	}
	
	@Transactional
	public void saveCard(Card card) {
		cardRepository.save(card);
	}
	
	public void deleteCard(int idCard) {
		cardRepository.deleteById(idCard);
	}

	public Optional<String> findColor(String name) {
		return cardRepository.findColor(name);
	}

	public Optional<Integer> findRange(String name) {
		return cardRepository.findRange(name);
	}
	
	public Optional<Integer> findDamage(String name) {
		return cardRepository.findDamage(name);
	}
}
