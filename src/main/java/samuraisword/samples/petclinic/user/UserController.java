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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import samuraisword.achievements.Achievement;
import samuraisword.samples.petclinic.pet.exceptions.DuplicatedUserNameException;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_USER_CREATE_FORM = "users/createUserForm";

	private final UserService userService;
	private final AuthoritiesService authoritiesService;

	@Autowired
	public UserController(UserService samuraiService, AuthoritiesService authoritiesService) {
		this.userService = samuraiService;
		this.authoritiesService = authoritiesService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);
		return VIEWS_USER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("user", user);
			return VIEWS_USER_CREATE_FORM;
		} else {

			try {
				this.userService.registerUser(user);
				authoritiesService.saveAuthorities(user.getUsername(), "user");
			} catch (DuplicatedUserNameException ex) {
				result.rejectValue("username", "duplicate", "already exists");
				return VIEWS_USER_CREATE_FORM;
			}

			// try catch
			// creating owner, user, and authority
			return "redirect:/";
		}
	}

	@GetMapping(value = "/users/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("user", new User());
		return "users/findUsers";

	}

	@GetMapping(value = "/users")
	public String processFindForm(User user, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (user.getUsername() == null) {
			user.setUsername(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<User> results = this.userService.findUserByUsername(user.getUsername());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("username", "notFound", "not found");
			return "users/findUsers";
		} else if (results.size() == 1) {
			// 1 owner found
			// user = results.iterator().next();
			// return "redirect:/users/" + user.getUsername();
			model.put("selections", results);
			return "users/usersList";
		} else {
			// multiple owners found
			model.put("selections", results);
			return "users/usersList";
		}
	}

	@GetMapping(value = "friendRequest")
	public String listRequestAll(Map<String, Object> model) {
		UserDetails userRequested = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<String> u = userService.listRequestAll(userRequested.getUsername());
		model.put("friendRequest", u);
		return "users/friendRequest";
	}
		
	@PostMapping(value = "friendRequest/SendRequest/{usernameProfile}")
	public String procesSendController(@PathVariable("usernameProfile") String usernameProfile) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String user1 = userDetails.getUsername();
		userService.sendRequested(user1, usernameProfile);
		return "welcome";
	}

	@PostMapping(value = "friendRequest/AcceptRequest/{usernameProfile}")
	public String acceptController(@PathVariable("usernameProfile") String usernameProfile) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String user1 = userDetails.getUsername();
		userService.acceptRequest(user1, usernameProfile);
		return "welcome";
	}

	@PostMapping(value = "friendRequest/declineRequest/{usernameProfile}")
	public String declineRequest(@PathVariable("usernameProfile") String usernameProfile) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String user1 = userDetails.getUsername();
		userService.declineRequest(user1, usernameProfile);
		return "welcome";
	}
}
