package fi.vaatteet.demo.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.vaatteet.demo.domain.User;
import fi.vaatteet.demo.domain.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * This class is used by spring security to authenticate and authorize user
 **/

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired

	private UserRepo repository;

	@Override

	public UserDetails loadUserByUsername(String

	username)

			throws UsernameNotFoundException {

		Optional<User> user = Optional.ofNullable(repository.findByUsername(username));

		UserBuilder builder = null;

		if (user.isPresent()) {

			User currentUser = user.get();

			builder = org.springframework.security.core.userdetails.User.withUsername(username);

			builder.password(currentUser.getPasswordHash());

			builder.roles("ADMIN");
			System.out.println("ADMIN");
//			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//
//			builder.authorities(authorities);

		} else {

			throw new UsernameNotFoundException("User not found.");

		}

		return builder.build();

	}

}
