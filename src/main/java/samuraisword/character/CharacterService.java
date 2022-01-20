package samuraisword.character;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.game.Game;
import samuraisword.game.GameService;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;

@Service
public class CharacterService {

	private CharacterRepository characterRepository;
	private CardService cardService;

	@Autowired
	public CharacterService(CharacterRepository characterRepository,
			CardService cardService) {
		this.characterRepository = characterRepository;
		this.cardService = cardService;
	}

	@Transactional
	public Collection<Character> findAll() {
		return characterRepository.findAll();
	}

	@Transactional
	public Character findByName(String name) {
		return characterRepository.findByName(name);
	}

	public Boolean execute(Player player) {
		Boolean res = false;
		if (player.getCharacter().getName().equals("Benkei")) {
			Integer distanceNow = player.getDistanceBonus();
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				statUp(player, "distanceBonus", 1);
				res = player.getDistanceBonus().equals(distanceNow + 1);
			}
		}
		if (player.getCharacter().getName().equals("Chiyome")) {
			player.setDisabled(false);
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				player.setDisabled(true);
			}
			
			res = player.isDisabled();
		}
		if (player.getCharacter().getName().equals("Ginchiyo")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				int n = player.getCurrentHearts();
				player.setCurrentHearts(player.getCurrentHearts() + 1);
				res = player.getCurrentHearts().equals(n + 1);
			}

		}
		
		if (player.getCharacter().getName().equals("Hideyoshi")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())
					&& player.getGame().getCurrentPlayer().equals(player)) {
				int n = player.getHand().size();
				Card card = player.getGame().getDeck().get(0);
				System.out.println("pepe"+player.getHand().size());
				player.getHand().add(card);
				System.out.println("pepe"+player.getHand().size());
				player.getGame().getDeck().remove(0);
				res = player.getHand().size()== (n+1);
			}
			
		}
		
		if (player.getCharacter().getName().equals("Goemon")) {
			Integer weaponsNow = player.getWeaponBonus();
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				statUp(player, "weaponBonus", 1);
				res = player.getWeaponBonus().equals(weaponsNow + 1);
			}
		}

		if (player.getCharacter().getName().equals("Kojiro")) {

			res = (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())
					&& player.getGame().getCurrentPlayer().equals(player));

		}
		if (player.getCharacter().getName().equals("Musashi")) {
			Integer damageNow = player.getDamageBonus();
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				statUp(player, "damageBonus", 1);
				res = player.getDamageBonus().equals(damageNow + 1);
			}
		}
		
		if (player.getCharacter().getName().equals("Tomoe")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())
					&& player.getCharacter().equals(player.getGame().getCurrentPlayer().getCharacter())) {
				int n = player.getHand().size();
				Card c = player.getGame().getDeck().get(0);
				player.getHand().add(c);
				res = player.getHand().size()== (n+1);
			}
		}
		
		if (player.getCharacter().getName().equals("Ushiwaka")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				int n = player.getHand().size();
				Card c = player.getGame().getDeck().get(0);
				player.getHand().add(c);
				res = player.getHand().size()== n+1;
			}
		}
		return res;
	}

	public void statUp(Player player, String stat, Integer bonus) {	
		if(stat.contains("distanceBonus")) player.setDistanceBonus(player.getDistanceBonus()+bonus);
		if(stat.contains("weaponBonus")) player.setWeaponBonus(player.getWeaponBonus()+bonus);
		if(stat.contains("damageBonus")) player.setDamageBonus(player.getDamageBonus()+bonus);
	}
	public void statDown(Player player, String stat, Integer bonus) {
			if(stat.equals("distanceBonus")) player.setDistanceBonus(player.getDistanceBonus()-bonus);
			if(stat.equals("weaponBonus")) player.setWeaponBonus(player.getWeaponBonus()-bonus);
			if(stat.equals("damageBonus")) player.setDamageBonus(player.getDamageBonus()-bonus);
	}
}
