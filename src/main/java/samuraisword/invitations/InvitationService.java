package samuraisword.invitations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.game.Game;
import samuraisword.game.GameRepository;
import samuraisword.player.Player;
import samuraisword.player.PlayerRepository;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserRepository;
import samuraisword.samples.petclinic.user.UserService;
@Service
public class InvitationService {
	
	private InvitationRepository invitationRepository;
	private PlayerRepository playerRepository;
	private UserRepository userRepository;
	private GameRepository gameRepository;
	
	
	public InvitationService(InvitationRepository invitationRepository, PlayerRepository playerRepository,
			 UserRepository userRepository, GameRepository gameRepository) {
		this.gameRepository = gameRepository;
		this.invitationRepository = invitationRepository;
		this.playerRepository= playerRepository;
		this.userRepository=userRepository;
	}
	
	
//	@Transactional
//	public Optional<Invitation> findById(int idInvitation){
//		return invitationRepository.findById(idInvitation);
//	}
	
	public Collection<Invitation> findAll(){
		return invitationRepository.findAll();
	}
	
	@Transactional
	public Invitation saveInvitation(Invitation inv){	
		return invitationRepository.save(inv);
	}
	
	
	@Transactional(readOnly=true)
	public Collection<Invitation> findAllByUser(String username) throws DataAccessResourceFailureException{
		return invitationRepository.findAllByUser(username);
	}
	
	
	public Optional<Invitation> findById(Integer invitationId) throws DataAccessResourceFailureException{
		return invitationRepository.findById(invitationId);
	}
	
	
	
	@Transactional(readOnly = true)
	public void sendInvitation(User userAddresse, User userSender, Game gameId) throws DataAccessException {
		
		
			Invitation inv=new Invitation();
			inv.setGame(gameId);
			inv.setUserAddresse(userAddresse);
			inv.setUserSender(userSender);
			invitationRepository.save(inv);
			
		
	}
	
	@Transactional
	public void acceptInvitation(Invitation inv) throws DataAccessException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Invitation> listInvitations= findAllByUser(userDetails.getUsername());
		
		invitationRepository.delete(inv);
		for(Invitation i: listInvitations) {
			if(i.getGame().equals(inv.getGame())) {
				invitationRepository.delete(i);
			}
		}
		
		
		Player player= new Player();
		player.setUser(inv.getUserAddresse());
		player.setGame(inv.getGame());			
	//	inv.getGame().getListPlayers().add(player);
		gameRepository.save(inv.getGame());
		userRepository.save(inv.getUserAddresse());
		playerRepository.save(player);	
		
		
	}
	
	@Transactional
	public void declineInvitation(Invitation inv)throws DataAccessException {
		invitationRepository.delete(inv);
		
	}
	
	@Transactional
	public void deleteInvitationsByGame(Game game) throws DataAccessException{
		invitationRepository.deleleInvitationByGame(game);
	}
	
	
}
