package samuraisword.samples.petclinic.card;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.character.CharacterRepository;
import samuraisword.comment.Comment;
import samuraisword.game.GameService;
import samuraisword.player.Player;
import samuraisword.samples.petclinic.user.User;

@Service
public class CardService {
	
	private CardRepository cardRepository;
	private GameService gameService;

	@Autowired
	public CardService(CardRepository CardRepository, GameService gameService) {
		this.cardRepository = CardRepository;
		this.gameService = gameService;
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
	
	public Boolean executeEquip(Player player, Integer i) {
		Boolean res= false;
		if (player.getHand().get(i).getName().equals("Armadura")){
			Integer bonus=player.getDistanceBonus();
				gameService.statUp(player, "distanceBonus", 1);
				res=bonus+1==player.getDistanceBonus();
			
		}else if (player.getHand().get(i).getName().equals("Concentracion")){
			Integer bonus=player.getWeaponBonus();
			gameService.statUp(player, "weaponBonus", 1);
			res=bonus+1==player.getWeaponBonus();
			
		}else if (player.getHand().get(i).getName().equals("Desenvainado")){
			Integer bonus=player.getDamageBonus();
			gameService.statUp(player, "damageBonus", 1);
			res=bonus+1==player.getDamageBonus();
			
		}
		
		
		return res;
	}
}
