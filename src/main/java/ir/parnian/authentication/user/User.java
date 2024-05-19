package ir.parnian.authentication.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Table(name = "user_tbl")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "username", unique=true)
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "active")
	private boolean active;

	@ManyToMany(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
	@JoinTable(name = "user_role_tbl",
	  joinColumns = @JoinColumn(name = "user_id"), 
	  inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList();

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", active=" + active
				+ ", roles=" + roles + "]";
	}
	
}
