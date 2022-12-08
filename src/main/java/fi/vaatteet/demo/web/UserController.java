package fi.vaatteet.demo.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fi.vaatteet.demo.domain.User;
import fi.vaatteet.demo.domain.UserRepo;

import fi.vaatteet.demo.AccountCredentials;
@Controller
public class UserController {
	@Autowired
	private UserRepo userRepo;
	

	@PostMapping("api/users")
	public ResponseEntity<User> createUser(@RequestBody AccountCredentials creds) {
		System.out.println(creds.toString());

		try {
			User user = new User();
			String pwd = creds.getPassword();
			BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			String hashPwd = bc.encode(pwd);
			user.setPasswordHash(hashPwd);
			user.setRole(creds.getRole());
			user.setUsername(creds.getUsername());
			if (userRepo.findByUsername(creds.getUsername()) != null) {
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
	
	@GetMapping("api/users/{username}")
	public ResponseEntity<User> getUser(Principal principal,  @PathVariable String username){
		//org.springframework.security.core.userdetails.User luokan olio. Printataan kokeilun vuoksi. 
		System.out.println(principal.toString());
		User user = userRepo.findByUsername(username);
		
		return ResponseEntity.status(200).body(user);
	}
}
