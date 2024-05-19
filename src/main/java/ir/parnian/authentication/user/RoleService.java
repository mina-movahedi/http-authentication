package ir.parnian.authentication.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public Role save(Role role) {
		return roleRepository.save(role);
	}
	
	public List<Role> getAllRoles(){
		ArrayList<Role> result = new ArrayList<>();
		for(Role role: roleRepository.findAll()) {
			result.add(role);
		}
		return result;
	}
	
	public Role findRoleByName(String name) {
		return roleRepository.findByName(name).get();
	}
}
