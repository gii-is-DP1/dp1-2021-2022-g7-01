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
import samuraisword.game.GameStatus;
import samuraisword.player.Player;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;

@Service
public class CharacterService {

	private CharacterRepository characterRepository;
	private GameService gameService;
	private CardService cardService;
	private PlayerService playerService;

	@Autowired
	public CharacterService(CharacterRepository characterRepository, GameService gameService, CardService cardService,
			PlayerService playerService) {
		this.characterRepository = characterRepository;
		this.gameService = gameService;
		this.cardService = cardService;
		this.playerService = playerService;
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
				gameService.statUp(player, "distanceBonus", 1);
				res = player.getDistanceBonus().equals(distanceNow + 1);
			}
		}
		if (player.getCharacter().getName().equals("Chiyome")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				player.setDisabled(true);
			}
			res = player.isDisabled();
		}
		if (player.getCharacter().getName().equals("Ginchiyo")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())
					&& player.getGame().getStatus().equals(player.getCharacter().getStatus())) {
				int n = player.getCurrentHearts();
				player.setCurrentHearts(player.getCurrentHearts() + 1);
				res = player.getCurrentHearts().equals(n + 1);
			}

		}

		if (player.getCharacter().getName().equals("Hanzo")) {
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())
					&& player.getGame().getStatus().equals(player.getCharacter().getStatus())) {
				Optional<Card> c = cardService.findByName("parada");
				res = player.getHand().contains(c.get());

			}

		}
		if (player.getCharacter().getName().equals("Goemon")) {
			Integer weaponsNow = player.getWeaponBonus();
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				gameService.statUp(player, "weaponBonus", 1);
				res = player.getWeaponBonus().equals(weaponsNow + 1);
			}
		}
		if (player.getCharacter().getName().equals("Ieyasu")) {
			res = player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())
					&& player.equals(player.getGame().getCurrentPlayer());
		}

		if (player.getCharacter().getName().equals("Kojiro")) {

			res = (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())
					&& player.getGame().getStatus().equals(player.getCharacter().getStatus())
					&& player.getGame().getCurrentPlayer().equals(player));

		}
		if (player.getCharacter().getName().equals("Musashi")) {
			Integer damageNow = player.getDamageBonus();
			if (player.getGame().getGamePhase().equals(player.getCharacter().getGamePhase())) {
				gameService.statUp(player, "damageBonus", 1);
				res = player.getDamageBonus().equals(damageNow + 1);
			}
		}
		return res;
	}

	public List<Player> checkCharacters(Game game) {
		List<Player> res = new ArrayList<Player>();
		for (Player p : game.getListPlayers()) {
			if (p.getCharacter().getName().equals("Ginchiyo") 
					|| p.getCharacter().getName().equals("Ushiwaka")
						|| p.getCharacter().getName().equals("Hanzo")) {

			} else {
				if (this.execute(p))
					res.add(p);
			}
		}
		return res;
	}

	public List<Player> changeStatus(Game game, GameStatus status) {
		game.setStatus(status);
		return checkCharacters(game);
	}
}
