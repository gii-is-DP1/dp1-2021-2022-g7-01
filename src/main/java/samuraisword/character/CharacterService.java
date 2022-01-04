package samuraisword.character;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.game.GameService;
import samuraisword.player.Player;

@Service
public class CharacterService {

	private CharacterRepository characterRepository;
	private GameService gameService;

	@Autowired
	public CharacterService(CharacterRepository characterRepository, GameService gameService) {
		this.characterRepository = characterRepository;
		this.gameService = gameService;
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
		Boolean res= false;
		if (player.getCharacter().getName().equals("Benkei")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				gameService.statUp(player, "distanceBonus", 1);
				res=player.getDistanceBonus().equals(1);
			}
		}
		if (player.getCharacter().getName().equals("Chiyome")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				player.setDisabled(true);
				
			}
			res=player.isDisabled();
		}
		if (player.getCharacter().getName().equals("Goemon")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				gameService.statUp(player, "weaponBonus", 1);
				res=player.getWeaponBonus().equals(1);
			}
		}
		if (player.getCharacter().getName().equals("Ieyasu")) {
			res=player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())
					&&player.equals(player.getGame().getCurrentPlayer());
		}
		if (player.getCharacter().getName().equals("Musashi")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				gameService.statUp(player, "damageBonus", 1);
				res=player.getDamageBonus().equals(1);
			}
		}
		return res;
	}
	
}
