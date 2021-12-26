package samuraisword.invitations;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InvitationRepository extends CrudRepository<Invitation, Integer>{
	
Collection<Invitation> findAll();

	
	
	@Query(value = "SELECT * FROM INVITATION WHERE USER_ADDRESSE=:username", nativeQuery=true)
	Collection<Invitation> findAllByUser(String username);
	
	
	
//	@Modifying
//	@Query(
//	  value = 
//	    "insert into INVITATION (USERS_SENDER, USER_ADDRESSE,GAME_ID) values (:userSender, :userAddresse, :game)",
//	  nativeQuery = true)
//	void sendInvitation(@Param("userSender") String USER_SENDER, @Param("userAddress") String USER_ADDRESSE,
//			@Param("game") Integer GAME_ID); 
//	
//	@Modifying
//	@Query(
//	  value = 
//	    "insert into PLAYERS(CURRENT_HEARTS, ES_INOFENSIVO, HONOR, MAX_HEARTS, ROL, WON_GAME,"
//	    + "	CHARACTER, GAME_ID, USERNAME) values (0,FALSE,0,0,1,FALSE,null,:game, :userAddresse)",
//	  nativeQuery = true)
//	void acceptInvitation(@Param("game") String GAME_ID, @Param("userAddresse") String USERNAME);
//	
//	@Modifying
//	@Query(value="delete from INVITATION where USER_ADDRESSE=:user1 AND GAME_ID=:game", nativeQuery=true)
//	void deleteInvitation(@Param("user1") String USER_ADDRESSE, @Param("game") String GAME_ID);
	
}
