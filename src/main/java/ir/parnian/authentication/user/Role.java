package ir.parnian.authentication.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "role_tbl")
@Entity
public class Role {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@ManyToMany(mappedBy = "roles", cascade = CascadeType.REMOVE)
	private List<User> user = new ArrayList<User>();
}
