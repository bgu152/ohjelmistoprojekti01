package fi.vaatteet.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fi.vaatteet.demo.domain.User;
import fi.vaatteet.demo.domain.UserRepo;

@Controller
public class UserController {
	@Autowired
	private UserRepo userRepo;

	@PostMapping("api/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {

		try {
			String pwd = user.getPasswordHash();
			BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			String hashPwd = bc.encode(pwd);
			user.setPasswordHash(hashPwd);
			if (userRepo.findByUsername(user.getUsername()) != null) {
				return ResponseEntity.status(409).build();
			} else {
				userRepo.save(user);
				return ResponseEntity.status(201).body(user);
			}

		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}

	}
	
	@GetMapping("api/users")
	public ResponseEntity<List<User>> getAllUsers(){
		try {
			List<User> users = (List<User>) userRepo.findAll();
			return ResponseEntity.ok().body(users);
			
		}catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
}