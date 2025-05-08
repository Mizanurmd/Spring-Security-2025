package com.cns.security.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cns.security.dto.UserDto;
import com.cns.security.repository.UserRepository;
import com.cns.security.serviceImp.UserManagementService;

@RestController
@RequestMapping("api/v1/")
public class OurUserController {

	private final UserRepository userRepository;
	private final UserManagementService userManagementService;

	public OurUserController(UserRepository userRepository, UserManagementService userManagementService) {
		super();
		this.userRepository = userRepository;
		this.userManagementService = userManagementService;
	}
	
	@PostMapping("/auth/register")
	public ResponseEntity<UserDto> regeister(@RequestBody UserDto userDto) {
		return ResponseEntity.ok(userManagementService.register(userDto));
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<UserDto>login(@RequestBody UserDto userDto){
		return ResponseEntity.ok(userManagementService.login(userDto));
		
	}
	
	@GetMapping("/admin/all")
	public ResponseEntity<UserDto>allUsers(){
		return ResponseEntity.ok(userManagementService.getAllUser());
	}

}
