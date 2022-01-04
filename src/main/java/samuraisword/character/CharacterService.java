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
	public CharacterService(CharacterRepository characterRepository,GameService gameService) {
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

	public void execute(Player player) {
		if (player.getCharacter().getName().equals("Benkei")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				gameService.statUp(player, "distanceBonus", 1);
			}
		}
	}
}
