package samuraisword.player;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.game.Game;


@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	
	@Transactional
	public Collection<Player> findAll(){
		return playerRepository.findAll();
	}
	
	@Transactional
	public Optional<Player> findById(int idPlayer) {
		return playerRepository.findById(idPlayer);
	}
	
	@Transactional
	public int playerCount() {
		return (int) this.playerRepository.count();
	}
	
	public void deletePlayer(int idPlayer) {
		playerRepository.deleteById(null);
	}
	
	public void deleteAllPlayers() {
		playerRepository.deleteAll();
	}
	
	@Transactional
	public void savePlayer(Player player) throws DataAccessException {
		playerRepository.save(player);		
	}	
}
