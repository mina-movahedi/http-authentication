package ir.parnian.authentication.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Secured("ADMIN")
	@PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public User createUser(@RequestBody User user) {
		return userService.save(user);
	}

	@Secured("ADMIN")
	@GetMapping(value = "/getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserListDto> getAllUsers() {

		UserListDto dto = new UserListDto();
		List<User> users = userService.getAllUsers();
		dto.setUserList(users);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
	}

	@Secured("ADMIN")
	@PostMapping(value = "/giveRolesToUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> giveRolesToUser(@RequestBody Long userId, @RequestBody String roleName) {
		try {
			User user = userService.giveRoleToUser(userId, roleName);
			if(user == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User or Role you are trying to add, is not exist.");
			}
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfull");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not Successfull");
		}

	}

}
