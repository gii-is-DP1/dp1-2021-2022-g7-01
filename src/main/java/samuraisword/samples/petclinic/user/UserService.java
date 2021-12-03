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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import samuraisword.samples.petclinic.owner.Owner;
import samuraisword.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import samuraisword.samples.petclinic.pet.exceptions.DuplicatedUserNameException;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	@Transactional(rollbackFor = DuplicatedUserNameException.class)
	public void registerUser(User user) throws DataAccessException, DuplicatedUserNameException {
		Optional<User> userAux=findUser(user.getUsername());
		if(userAux.isPresent()) {
			throw new DuplicatedUserNameException();
		}else {
			saveUser(user);
		}
	}
	
	@Transactional(rollbackFor = DuplicatedUserNameException.class)
	public void updateUser(User user) throws DataAccessException, DuplicatedUserNameException {
		Optional<User> userAux=findUser(user.getUsername());
		if(userAux.isPresent()) {
			throw new DuplicatedUserNameException();
		}else {
			saveUser(user);
		}
	}
	
	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}
	

	@Transactional(readOnly = true)
	public Collection<User> findUserByUsername(String username) throws DataAccessException {
		return userRepository.findByUsername(username);
	}
	
	public Collection<String> getAllFriendOf(String username) {
		Collection<String>listRes = userRepository.listFriendAll1(username);
		listRes.addAll(userRepository.listFriendAll2(username));
		return listRes;
	}

	@Transactional(readOnly = true)
	public Collection<String> listRequestAll(String username) throws DataAccessException {
		return userRepository.listRequestAll(username);
	}
	
	@Transactional(readOnly = true)
	public void sendRequested(String username1, String username2) throws DataAccessException {
		Collection<String>ListRequest = userRepository.listRequestAll(username1);
		Collection<String>ListRequest2 = userRepository.listRequestAll(username2);
		Collection<String>ListFriend = getAllFriendOf(username1);
		if((!ListRequest.contains(username2) && !ListRequest2.contains(username1)) && !ListFriend.contains(username2)) {
			userRepository.sendRequest(username1,username2);
		}
	}
	
	@Transactional(readOnly = true)
	public void acceptRequest(String username1, String username2) throws DataAccessException {
		userRepository.deleteRequest(username1,username2);
		userRepository.deleteRequest(username2,username1);
		userRepository.acceptRequest(username1, username2);
	}
	
	@Transactional(readOnly = true)
	public void declineRequest(String username1, String username2) throws DataAccessException {
		userRepository.deleteRequest(username1,username2);
		userRepository.deleteRequest(username2,username1);
	}
}
