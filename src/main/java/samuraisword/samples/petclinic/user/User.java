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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.core.style.ToStringCreator;

import lombok.Getter;
import lombok.Setter;
import samuraisword.player.Player;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@NotEmpty
	@NotBlank
	String username;

	@NotEmpty
	@NotBlank
	@Email
	String email;
	
	@NotEmpty
	@NotBlank
	@Size(min=8, message="Necesita como mínimo 8 caracteres")
	String password;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	List<Player> listPlayers;
	
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
	
	
	public boolean isNew() {
		return this.username == null;
	}
	
	
	
	
	
	
	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("username", this.getUsername()).append("new", this.isNew())
				.append("email", this.getEmail()).toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		User other = (User) obj;
		if (email.equals(other.email))
			return false;
		if (username.equals(other.username))
			return false;
		return true;
	}
	
	
	
	
}
