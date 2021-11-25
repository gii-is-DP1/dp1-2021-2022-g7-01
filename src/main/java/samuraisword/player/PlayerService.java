package samuraisword.player;

import java.util.Collection;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import samuraisword.game.Game;

import samuraisword.logros.RolType;



@Service
public class PlayerService {
	
	 
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
	
	@Transactional
	public void savePlayers(@Valid Player player) {
		playerRepository.save(player);
	}
	
	@Transactional
	public void deletePlayer(int idPlayer) {
		playerRepository.deleteById(idPlayer);
	}
	
	@Transactional
	public void deleteAllPlayers() {
		playerRepository.deleteAll();
	}

	
	@Transactional
	public void savePlayer(Player player) throws DataAccessException {
		playerRepository.save(player);		
	}	


		@Transactional(readOnly = true)
	public Collection<RolType> findRolTypes() throws DataAccessException {
		return playerRepository.findRolTypes();
	}

}
