package samuraisword.comment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import samuraisword.samples.petclinic.model.BaseEntity;
import samuraisword.samples.petclinic.user.User;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String body;
	     
	private Date createDate;
	
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
}
