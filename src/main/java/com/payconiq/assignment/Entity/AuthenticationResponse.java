package com.payconiq.assignment.Entity;

import java.io.Serializable;

/**
 * @author User
 * Response pojo class for authentication
 *
 */
public class AuthenticationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String jwt;

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
}