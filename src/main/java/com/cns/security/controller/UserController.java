package com.cns.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	@GetMapping()
	public String testUser() {
		String mes = "Welcome to Spring Security-2025";
		return mes;
	}

}
