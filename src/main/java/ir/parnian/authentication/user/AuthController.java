package ir.parnian.authentication.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class AuthController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AuthController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody User user) {
		String result = userService.getRollesOfUser(user.getUsername(), user.getPassword());
		if (result == null || result.equals("401")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The username or password is not correct.");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
	}

	@PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registration(@RequestBody User user) {
		Role publicRole = roleService.findRoleByName("PUBLIC");
		List<Role> roles = new ArrayList<Role>();
		roles.add(publicRole);
		user.setRoles(roles);
		try {
			userService.save(user);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("the user has been saved successfully.");
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body("The username is used for someone else before.");
		}

	}
}
