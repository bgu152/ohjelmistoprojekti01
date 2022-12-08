package fi.vaatteet.demo.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

//import fi.vaatteet.demo.domain.User;
import fi.vaatteet.demo.domain.UserRepo;



/**
 * This class is used by spring security to authenticate and authorize user
 **/

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired

	private UserRepo repository;

	@Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		 

        System.out.println("BACKEND: UserDetails loadUserByUsername " + username);

        fi.vaatteet.demo.domain.User user = repository.findByUsername(username);       

        System.out.println("BACKEND: UserDetails loadUserByUsername " + username);       

        return new User(user.getUsername(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole()));

}

}
