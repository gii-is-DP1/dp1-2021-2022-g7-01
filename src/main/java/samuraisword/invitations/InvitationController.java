package samuraisword.invitations;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import antlr.collections.List;
import samuraisword.game.GameService;
import samuraisword.samples.petclinic.user.UserService;


@Controller
public class InvitationController {

	
	
	
private InvitationService invitationService;
private UserService userService;
private GameService gameService;
	
	
	public InvitationController(UserService userService, InvitationService invitationService, GameService gameService) {
	
		
		this.invitationService = invitationService;
		this.userService = userService;
		this.gameService = gameService;
	}
	
	
	@GetMapping(value= "/invitation")
	public String listInvitations(Map<String,Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Invitation> listInvitations= invitationService.findAllByUser(userDetails.getUsername());
		
		model.put("listInvitations",listInvitations);
		model.put("userName",userDetails.getUsername());
		return "game/createGame";
	}
	
	
	@PostMapping(value="/game/{gameId}/invitation/{userAddresse}")
	public String processInvitationController(@PathVariable("gameId") Integer gameId,@PathVariable("userAddresse") String userAddresse) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Invitation> listInvitations= invitationService.findAllByUser(userDetails.getUsername());
		//userAddresse destinatario invitacion
		//recoger el gameId de la sala en la que nos encontramos
		Invitation inv=new Invitation();
		inv.setGame(gameService.findById(gameId).get());
		inv.setUserAddresse(userService.findUser(userAddresse).get());
		inv.setUserSender(userService.findUser(userDetails.getUsername()).get());
		
		
		invitationService.saveInvitation(inv);
		return "redirect:/game/"+gameId;
	}
	
	
	@PostMapping(value = "/game/{gameId}/invitation/accept/{invitationId}")
	public String acceptController(@PathVariable("gameId") Integer gameId, @PathVariable("invitationId") Integer invitationId) {
		Optional<Invitation> inv= invitationService.findById(invitationId);
		invitationService.acceptInvitation(inv.get());
		return "redirect:/game/"+gameId;
	}
	
	
	@PostMapping(value = "/game/{gameId}/invitation/decline/{invitationId}")
	public String declineInvitation(@PathVariable("gameId") Integer gameId, @PathVariable("invitationId") Integer invitationId) {
		Optional<Invitation> inv= invitationService.findById(invitationId);
		invitationService.declineInvitation(inv.get());

		return "redirect:/game/new";
	}
	
	
	
	
	
	
}
