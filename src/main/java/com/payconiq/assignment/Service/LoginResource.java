package com.payconiq.assignment.Service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author User
 * Controller for login
 */
@Controller
public class LoginResource {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}