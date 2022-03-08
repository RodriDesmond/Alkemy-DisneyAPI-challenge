package org.alkemy.campus.challenge.security.services.impl;
import org.alkemy.campus.challenge.entity.ERole;
import org.alkemy.campus.challenge.entity.Role;
import org.alkemy.campus.challenge.entity.User;
import org.alkemy.campus.challenge.payload.request.SignupRequest;
import org.alkemy.campus.challenge.payload.response.MessageResponse;
import org.alkemy.campus.challenge.repository.RoleRepository;
import org.alkemy.campus.challenge.repository.UserRepository;
import org.alkemy.campus.challenge.security.services.UserService;
import org.alkemy.campus.challenge.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder encoder;

	private final EmailService emailService;

	private final RoleRepository roleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository,
	                       PasswordEncoder encoder,
	                       EmailService emailService,
	                       RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.emailService = emailService;
		this.roleRepository = roleRepository;
	}

	@Override
	public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest) throws IOException {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		strRoles.forEach(role -> {
			switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
			}
		});
		user.setRoles(roles);
		userRepository.save(user);
		emailService.sendTextEmail(user.getEmail());
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}