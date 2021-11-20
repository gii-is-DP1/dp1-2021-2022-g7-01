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

import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class CardController {

	private static final String FORM_Card = "cards/cardsList";

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

}