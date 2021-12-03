package samuraisword.samples.petclinic.card;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import samuraisword.achievements.Achievement;
import samuraisword.comment.Comment;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class CardController {

	private static final String FORM_CARD = "cards/formCard";

	private final CardService CardService;
	private final UserService userService;

	@Autowired
	public CardController(CardService CardService, UserService userService) {
		this.CardService = CardService;
		this.userService = userService;
	}

	@GetMapping(value = { "/cards" })
	public String listCards(Map<String, Object> model) {
		Collection<Card> listCards = CardService.findAll();
		model.put("cards", listCards);
		return "cards/cardsList";
	}

	@GetMapping(value = { "/cards/new" })
	public String newAchievementForm(Map<String, Object> model) {
		Card card = new Card();
		model.put("card", card);
		return FORM_CARD;
	}

	@Valid
	@PostMapping(value = "/cards/new")
	public String processCreationForm(@Valid Card card, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("cards", card);
			return FORM_CARD;
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			card.setUser(user);
			CardService.saveCard(card);
			return "redirect:/cards";
		}
	}
	

	@GetMapping(value = { "/cards/edit/{id_card}" })
	public String editCardForm(@PathVariable("id_card") int idCard, Map<String, Object> model) {
		Card card = CardService.findById(idCard).get();
		model.put("card", card);
		return FORM_CARD;
	}
	
	@PostMapping(value = { "/cards/edit/{id_card}" })
	public String processEditForm(@PathVariable("id_card") int idCard, @Valid Card card, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("card", card);
			return FORM_CARD;
		}
		Card cardToUpdate = CardService.findById(idCard).get();
		BeanUtils.copyProperties(card, cardToUpdate, "id","user");
		CardService.saveCard(cardToUpdate);
		return "redirect:/cards";
	}
	
	@GetMapping(value = { "/cards/delete/{id_card}" })
	public String deleteCommentForm(@PathVariable("id_card") int idCard, Map<String, Object> model) {
		CardService.deleteCard(idCard);
		return "redirect:/cards";
	}
}