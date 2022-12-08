package fi.vaatteet.demo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fi.vaatteet.demo.domain.User;
import fi.vaatteet.demo.domain.UserRepo;

import fi.vaatteet.demo.service.JwtService;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepo userRepo;

	@Override

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, java.io.IOException {

			
		
		// Get token from the Authorization header

		String jws = request.getHeader

		(HttpHeaders.AUTHORIZATION);

		if (jws != null) {

			// Verify token and get user

			String user = jwtService.getAuthUser(request);
			

			//Get user from database
			
			User userFromDB = userRepo.findByUsername(user);
			
			log.info("doFilterInternal: " + user.toString());

			// Authenticate
			
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(userFromDB.getRole()));

			Authentication authentication =
					new UsernamePasswordAuthenticationToken(user, null, authorities);

			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);

	}

}