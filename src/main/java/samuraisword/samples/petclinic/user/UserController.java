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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping(value = "/users/delete/{id_user}")
	public String processDeleteForm(@PathVariable("id_user") String id_user, Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		if(userDetails.getAuthorities().toString().contains("admin")) {
			userService.deleteUser(id_user);
			if(user.getUsername().equals(id_user)) {
			return "redirect:/logout";
		}
		}
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/users/update/{id_user}")
	public String processUpdateForm(@PathVariable("id_user") String id_user, Map<String, Object> model) {		
		Optional<User> userOptional = userService.findUser(id_user);
		if (userOptional.isEmpty() || !userOptional.get().getUsername().equals(id_user)) {
			return "exception";
		} else {
			model.put("user", userOptional.get());
			return "users/update";
		}
	}
	
	
	@PostMapping(value = "/users/update/user")
	public String processUpdate2Form(@Valid User user, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("user", user);
			return "users/update";
		} else {
			userService.saveUser(user);
			return "redirect:/";
		}
	}
	

	@GetMapping(value = "/users/friends/delete/{id_user}")
	public String processDeleteFriendForm(@PathVariable("id_user") String id_user, Map<String, Object> model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUser(userDetails.getUsername()).get();
		userService.deleteFriends(id_user, user.getUsername());
		
		return "redirect:/users/profile/"+user.getUsername();
	}
	
	@GetMapping(value = "/users/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("user", new User());
		return "users/findUsers";

	}

	@GetMapping(value = "/users/{page}")
	public String processFindForm(@PathVariable("page") int page, User user, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (user.getUsername() == null) {
			user.setUsername(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<User> results = this.userService.findUserByUsername(user.getUsername(),page);
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("username", "notFound", "not found");
			return "users/findUsers";
		} else if (results.size() == 1) {
			// 1 owner found
			// user = results.iterator().next();
			// return "redirect:/users/" + user.getUsername();
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user1 = userService.findUser(userDetails.getUsername()).get();
			Collection<String>listFriend = userService.getAllFriendOf(user1.getUsername());
			
			model.put("listFriend", listFriend);
			model.put("username", user1.getUsername());
			List<Integer>lPages = new ArrayList<Integer>();
			if(this.userService.nPagesByUsername(user.getUsername())%5==0) {
				for(int i=0; i<this.userService.nPagesByUsername(user.getUsername()); i++) {
					lPages.add(i);
				}
			}else {
				for(int i=0; i<=this.userService.nPagesByUsername(user.getUsername()); i++) {
					lPages.add(i);
				}
			}
			model.put("pages", lPages); 
			// multiple owners found
			model.put("authority", userDetails.getAuthorities().toString().contains("admin"));
			model.put("currentPage", page);
			model.put("selections", results);
			return "users/usersList";
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user1 = userService.findUser(userDetails.getUsername()).get();
			Collection<String>listFriend = userService.getAllFriendOf(user1.getUsername());
			
			model.put("listFriend", listFriend);
			model.put("username", user1.getUsername());
			List<Integer>lPages = new ArrayList<Integer>();
			if(this.userService.nPagesByUsername(user.getUsername())%5==0) {
				for(int i=0; i<this.userService.nPagesByUsername(user.getUsername()); i++) {
					lPages.add(i);
				}
			}else {
				for(int i=0; i<=this.userService.nPagesByUsername(user.getUsername()); i++) {
					lPages.add(i);
				}
			}
			
			model.put("pages", lPages);
//			if(listFriend.contains(u.getUsername()) || u.getUsername().equals(user1.getUsername())) {				
//				b=true;
//			}
			
			
			// multiple owners found
			model.put("authority", userDetails.getAuthorities().toString().contains("admin"));
			model.put("currentPage", page);
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
		if(!user1.equals(usernameProfile)) {
			userService.sendRequested(user1, usernameProfile);
		}
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
