package samuraisword.cardhand;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.comment.Comment;

@Service
public class CardHandService {
	
	private CardHandRepository cardHandRepository;
	
	@Autowired
	public CardHandService (CardHandRepository cardHandRepository) {
		this.cardHandRepository = cardHandRepository;
	}
	
	public Collection<CardHand> findAll(){
		return cardHandRepository.findAll();
	}
	
	@Transactional
	public void saveCardHand(CardHand cardHand) {
		cardHandRepository.save(cardHand);
	}
	
	public Optional<CardHand> findById(int idCardHand) {
		return cardHandRepository.findById(idCardHand);
	}
	
	public void deleteCardHand(int idCardHand) {
		cardHandRepository.deleteById(idCardHand);
	}

	public void save(@Valid CardHand cardHand) {
		cardHandRepository.save(cardHand);
		
	}
	
}
