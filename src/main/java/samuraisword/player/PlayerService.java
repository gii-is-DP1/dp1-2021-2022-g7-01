package samuraisword.player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.achievements.RolType;
import samuraisword.game.Game;
import samuraisword.samples.petclinic.user.User;



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
	public Optional<Player> findByUser(User user) {
		return playerRepository.findByUser(user);
	}
	
	@Transactional
	public Player findByUser(String user) {
		return playerRepository.findByUser(user);
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
	
	@Transactional
	public Player findPlayerByUsernameAndGame(String username, Game game) throws DataAccessException {
		return game.getListPlayers().stream().filter(x->x.getUser().getUsername().equals(username)).findFirst().get();		
	}
}
