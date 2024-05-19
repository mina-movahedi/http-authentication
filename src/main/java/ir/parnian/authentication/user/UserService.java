package ir.parnian.authentication.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public User save(User user) {
		Optional<User> oldUser = userRepository.findByUsername(user.getUsername());
		if(oldUser.isPresent()) {
			throw new IllegalStateException("The username is used by someone else before.");
		}
		String pass = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(pass);
		return userRepository.save(user);
	}

	
	public List<User> getAllUsers() {
		ArrayList<User> result = new ArrayList<>();
		for (User user : userRepository.findAll()) {
			result.add(user);
		}
		return result;
	}

	public String getRollesOfUser(String username, String password) {
		try {
			Optional<User> optionalUser = userRepository.findByUsername(username);
			boolean isPassCorrect = false;
			User user = null;
			if (optionalUser.isPresent()) {
				user = userRepository.findByUsername(username).get();
				isPassCorrect = new BCryptPasswordEncoder().matches(password, user.getPassword());
				if (isPassCorrect) {
					List<Role> roles = user.getRoles();
					return roles.stream().map(r -> r.toString()).collect(Collectors.joining(","));
				}
			}
			return "401";
		} catch (Exception e) {
			e.printStackTrace();
			return "401";
		}

	}

	public User giveRoleToUser(Long userId, String roleName) {
		Optional<User> optionalUser = userRepository.findById(userId);
		Optional<Role> optionalRole = roleRepository.findByName(roleName);
		User user = new User();
		if(!optionalUser.isPresent() || !optionalRole.isPresent()) {
			return null;
		} else {
			user = optionalUser.get();	
		}
		List<Role> roles = user.getRoles();
		if(!roles.contains(optionalRole.get())) {
			roles.add(optionalRole.get());
			user.setRoles(roles);
			return userRepository.save(user);
		}
		return user;
	}
}
