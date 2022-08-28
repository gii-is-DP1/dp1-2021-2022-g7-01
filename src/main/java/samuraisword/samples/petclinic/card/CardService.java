package samuraisword.samples.petclinic.card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.game.Game;
import samuraisword.player.Player;

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

	public Optional<Card> findByName(String name) {
		return cardRepository.findByName(name);
	}

	public Optional<RedCard> findRedCardByName(String name) {
		return cardRepository.findRedCardByName(name);
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

	public List<Card> createDeck() {
		List<Card> gameDeck = new ArrayList<>();
		for (Card card : cardRepository.findAll()) {
			for (int i = 0; i < card.getCardsPerDeck(); i++) {
				if (card.getColor().equals("Red")) {
					String name = card.getName();
					RedCard redCard = (RedCard) card;
					redCard.setRange(cardRepository.findRange(name).get());
					redCard.setDamage(cardRepository.findDamage(name).get());
					gameDeck.add(redCard);
				} else {
					gameDeck.add(card);
				}
			}
		}
		Collections.shuffle(gameDeck);
		return gameDeck;
	}
	
	public void shuffleDeckInGame(Game game) {
		List<Card> discard= game.getDiscardPile();
		Collections.shuffle(discard);
		for(Card card1:discard) {
			game.getDeck().add(card1);
		}
		
		List<Card> newDiscard = new ArrayList<Card>();
		game.setDiscardPile(newDiscard);
		
		
	}
	
	
	

	public Boolean executeEquip(Player player, Integer i) {
		Boolean res = false;
		if (player.getHand().get(i).getName().equals("Armadura")) {
			Integer bonus = player.getDistanceBonus();
			this.statUp(player, "distanceBonus", 1);
			res = bonus + 1 == player.getDistanceBonus();

		} else if (player.getHand().get(i).getName().equals("Concentracion")) {
			Integer bonus = player.getWeaponBonus();
			this.statUp(player, "weaponBonus", 1);
			res = bonus + 1 == player.getWeaponBonus();

		} else if (player.getHand().get(i).getName().equals("Desenvainado")) {
			Integer bonus = player.getDamageBonus();
			this.statUp(player, "damageBonus", 1);
			res = bonus + 1 == player.getDamageBonus();

		}
		return res;
	}

	public void statUp(Player player, String stat, Integer bonus) {
		if (stat.contains("distanceBonus"))
			player.setDistanceBonus(player.getDistanceBonus() + bonus);
		if (stat.contains("weaponBonus"))
			player.setWeaponBonus(player.getWeaponBonus() + bonus);
		if (stat.contains("damageBonus"))
			player.setDamageBonus(player.getDamageBonus() + bonus);
	}

	public void statDown(Player player, String stat, Integer bonus) {
		if (stat.equals("distanceBonus"))
			player.setDistanceBonus(player.getDistanceBonus() - bonus);
		if (stat.equals("weaponBonus"))
			player.setWeaponBonus(player.getWeaponBonus() - bonus);
		if (stat.equals("damageBonus"))
			player.setDamageBonus(player.getDamageBonus() - bonus);
	}

	public void removeCardByName(String cardName, List<Card> listCard) {
		for (int i = 0; i < listCard.size(); i++) {
			if (listCard.get(i).getName().equals(cardName)) {
				listCard.remove(i);
				break;
			}
		}
	}

	public void addCardByName(String cardName, List<Card> from, List<Card> to) {
		for (int i = 0; i < from.size(); i++) {
			if (from.get(i).getName().equals(cardName)) {
				to.add(from.get(i));
				break;
			}
		}
	}

	public void discard(String cardName, List<Card> from, List<Card> to) {
		addCardByName(cardName, from, to);
		removeCardByName(cardName, from);
	}

}
