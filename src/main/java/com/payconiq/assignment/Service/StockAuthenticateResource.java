package com.payconiq.assignment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payconiq.assignment.Entity.AuthenticationRequest;
import com.payconiq.assignment.Entity.AuthenticationResponse;
import com.payconiq.assignment.Security.CustomSecurityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API for JWT Authentication")
@RestController
public class StockAuthenticateResource {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomSecurityUtil securityUtil;

	@Autowired
	private SecurityUserDetailsService userDetailsService;

	@ApiOperation("Rest Api to authenciate and generate jwt based on username and password")
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = securityUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
