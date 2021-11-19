/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package samuraisword.samples.petclinic.user;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import samuraisword.samples.petclinic.owner.Owner;
import samuraisword.samples.petclinic.owner.OwnerService;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_OWNER_CREATE_FORM = "users/createOwnerForm";

	private final OwnerService ownerService;
	private final UserService userService;

	@Autowired
	public UserController(OwnerService clinicService, UserService userService) {
		this.ownerService = clinicService;
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			this.ownerService.saveOwner(owner);
			return "redirect:/";
		}
	}

	@GetMapping(value = "users/profile/{usernameProfile}")
	public String viewProfile(@PathVariable("usernameProfile") String usernameProfile, Map<String, Object> model) {
		Optional<User> userOptional = userService.findUser(usernameProfile);
		if(userOptional.isEmpty()) {
			return "exception";
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("userProfile", userOptional.get());
			model.put("username", userDetails.getUsername());
			return "users/profile";
		}
	}
	
	@PostMapping(value = "users/profile/edit/{usernameProfile}")
	public String editProfile(@PathVariable("usernameProfile") String usernameProfile, Map<String, Object> model) {
		Optional<User> userOptional = userService.findUser(usernameProfile);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userOptional.isEmpty() || userOptional.get().getUsername().equals(userDetails.getUsername())) {
			return "exception";
		} else {
			model.put("userProfile", userOptional.get());
			model.put("username", userDetails.getUsername());
			return "users/editProfile";
		}
	}
}
