package samuraisword.samples.petclinic.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{

	@Id
	String username;
	
	String password;
	
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

	@ManyToMany
	@JoinTable(
			  name = "friends", 
			  joinColumns = @JoinColumn(name = "id_user"), 
			  inverseJoinColumns = @JoinColumn(name = "id_user_friend"))
	private List<User> listFriends;
	
	@ManyToMany
	@JoinTable(
			  name = "friends", 
			  joinColumns = @JoinColumn(name = "id_user_friend"), 
			  inverseJoinColumns = @JoinColumn(name = "id_user"))
	private List<User> listFriendsOf;
	
	public List<User> getListAllFriends() {
		List<User> listAllFriends = new ArrayList<>(listFriends);
		listAllFriends.addAll(listFriendsOf);
		return listAllFriends;
	}
}
