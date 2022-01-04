package samuraisword.samples.petclinic.user;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;




public interface UserRepository extends  CrudRepository<User, String>{
	
	
	
	@Query("SELECT DISTINCT user FROM User user WHERE user.username LIKE :username%")
	public Collection<User> findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT ID_USER FROM FRIEND_REQUESTS WHERE ID_USER_REQUESTED=:userRequest", nativeQuery=true)
	Collection<String> listRequestAll(String userRequest);
	
	@Query(value = "SELECT ID_USER FROM FRIEND_REQUESTS WHERE ID_USER=:userRequest", nativeQuery=true)
	Collection<String> listSendRequestAll(String userRequest);
	
	@Modifying
	@Query(
	  value = 
	    "insert into FRIEND_REQUESTS (ID_USER, ID_USER_REQUESTED) values (:user, :userRequested)",
	  nativeQuery = true)
	void sendRequest(@Param("user") String ID_USER, @Param("userRequested") String ID_USER_REQUESTED);
	
	@Query(value = "SELECT ID_USER_FRIEND FROM FRIENDS WHERE ID_USER=:user", nativeQuery=true)
	Collection<String> listFriendAll1(String user);
	
	@Query(value = "SELECT ID_USER FROM FRIENDS WHERE ID_USER_FRIEND=:user", nativeQuery=true)
	Collection<String> listFriendAll2(String user);
	
	@Modifying
	@Query(
	  value = 
	    "insert into FRIENDS (ID_USER, ID_USER_FRIEND) values (:user, :userFriend)",
	  nativeQuery = true)
	void acceptRequest(@Param("user") String ID_USER, @Param("userFriend") String ID_USER_FRIEND);
	
	@Modifying
	@Query(value="delete from FRIEND_REQUESTS where ID_USER_REQUESTED=:user2 AND ID_USER=:user1", nativeQuery=true)
	void deleteRequest(@Param("user2") String ID_USER_REQUESTED, @Param("user1") String ID_USER);
}
