package samuraisword.samples.petclinic.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;

import lombok.Getter;
import lombok.Setter;
import samuraisword.samples.petclinic.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "users")


public class User {


	
	@Id
	String username;
	
	String email;
	
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
	
	
	public boolean isNew() {
		return this.username == null;
	}
	
	
	
	
	
	
	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("username", this.getUsername()).append("new", this.isNew())
				.append("email", this.getEmail()).toString();
	}
	
	
	
	
}
