package samuraisword.logros;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import samuraisword.samples.petclinic.user.User;
import samuraisword.samples.petclinic.user.UserService;

@Controller
public class LogroController {

	private static final String FORM_LOGRO = "logros/formLogros";

	private final LogroService logroService;
	private final UserService userService;

	@Autowired
	public LogroController(LogroService logroService, UserService userService) {
		this.logroService = logroService;
		this.userService = userService;
	}

	@GetMapping(value = { "/logros" })
	public String listLogros(Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<Logro> listLogros = logroService.findAll();
		model.put("listLogros", listLogros);
		model.put("username", userDetails.getUsername());
		
		return "logros/listLogros";
	}

	@GetMapping(value = { "/logros/new" })
	public String newLogroForm(Map<String, Object> model) {
		Logro logro = new Logro();
		model.put("logro", logro);
		return FORM_LOGRO;
	}

	@Valid
	@PostMapping(value = "/logros/new")
	public String processCreationForm(@Valid Logro logro, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("logros", logro);
			return FORM_LOGRO;
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userService.findUser(userDetails.getUsername()).get();
			logro.setUser(user);
			logroService.saveLogros(logro);
			return "redirect:/logros";
		}
	}
	
	@ModelAttribute("types1")
	public Collection<LogroType> populatelogroTypes() {
		return this.logroService.findLogroTypes();
	}
	
	@ModelAttribute("types2")
	public Collection<RolType> populateRolTypes() {
		return this.logroService.findRolTypes();
	}
	
	@GetMapping(value = { "/logros/edit/{id_logro}" })
	public String editLogroForm(@PathVariable("id_logro") int idLogro, Map<String, Object> model) {
		Logro logro = logroService.findById(idLogro).get();
		model.put("logros", logro);
		return FORM_LOGRO;
	}
	
	@Valid
	@PostMapping(value = { "/logros/edit/{id_logro}" })
	public String processEditForm(@PathVariable("id_logro") int idLogro, @Valid Logro logro, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("logros", logro);
			return FORM_LOGRO;
		}
		Logro logroToUpdate = logroService.findById(idLogro).get();
		BeanUtils.copyProperties(logro, logroToUpdate, "id","user");
		logroService.saveLogros(logroToUpdate);
		return "redirect:/logros";
	}
	
	@GetMapping(value = { "/logros/delete/{id_logro}" })
	public String deleteLogrosForm(@PathVariable("id_logro") int idLogro, Map<String, Object> model) {
		logroService.deleteLogro(idLogro);
		return "redirect:/logros";
	}
}
