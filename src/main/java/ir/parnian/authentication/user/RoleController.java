package ir.parnian.authentication.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class RoleController {
	private final RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@PostMapping(value = "/createRole", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Role createRole(@RequestBody Role role) {
		return roleService.save(role);
	}

	@GetMapping(value = "/getAllRoles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoleListDto> getAllRoles() {

		RoleListDto dto = new RoleListDto();
		List<Role> roles = roleService.getAllRoles();
		dto.setRoleList(roles);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
	}
}
