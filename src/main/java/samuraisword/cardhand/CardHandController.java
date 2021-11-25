package samuraisword.cardhand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import samuraisword.comment.Comment;
import samuraisword.player.PlayerService;
import samuraisword.samples.petclinic.card.Card;
import samuraisword.samples.petclinic.card.CardService;
import samuraisword.samples.petclinic.user.UserService;

public class CardHandController {
	private static final String FORM_CARDHAND = "cardhands/formCardHand";
	
	private final PlayerService playerService;
	private final CardHandService cardHandService;
	private final CardService cardService;
	
	@Autowired
	public CardHandController(CardHandService cardHandService, PlayerService playerService, CardService cardService) {
		this.playerService = playerService;
		this.cardHandService = cardHandService;
		this.cardService = cardService;
	}
	
	@GetMapping(value = { "/cardHands" })
	public String listCardHands(Map<String, Object> model) {
		Collection<CardHand> listCardHands = cardHandService.findAll();
		model.put("listCards", listCardHands);
		return "comments/listCards";
	}
	
	@GetMapping(value = { "cardHands/new"})
	public String newCardHandForm(Map<String, Object> model) {
		CardHand cardHand = new CardHand();
		model.put("cardHand", cardHand);
		return FORM_CARDHAND;
	}
	
	@PostMapping(value= {"cardHands/new"})
	public String processCreationForm(@Valid CardHand cardHand,  BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.put("cardHand", cardHand);
			return FORM_CARDHAND;
		} 
		else {
			Collection<Card> allCards = cardService.findAll();
			List<Card> deck = new ArrayList<>();
			
			for(Card card : allCards) {
				Integer frequencyOfAppearance = card.getCardsPerDeck();
				for(int i = 0; i < frequencyOfAppearance; i++) {
					deck.add(card);
				}
		}
	  
		cardHand.setCardList(deck);
						
		cardHandService.save(cardHand);
		
		return "redirect:/cardHands";
		}
		
		
		
	}
	
	
	
	
	
	
	
}
	
