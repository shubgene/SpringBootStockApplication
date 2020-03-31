package com.payconiq.assignment.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author User
 * User Details Service  to validate user.
 * using username and password from application properties.
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private Environment env;
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		return new User(env.getProperty("stock.app.username"), env.getProperty("stock.app.password"), new ArrayList<>());
	}
}