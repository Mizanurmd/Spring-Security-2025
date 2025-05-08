package com.cns.security.serviceImp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cns.security.dto.UserDto;
import com.cns.security.entity.OurUser;
import com.cns.security.repository.UserRepository;

@Service
public class UserManagementService {
	
	private UserRepository usersRepo;

	private JwtUtil jwtUtil;

	private AuthenticationManager authenticationManager;

	private PasswordEncoder passwordEncoder;

	public UserManagementService(UserRepository usersRepo, JwtUtil jwtUtil, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		super();
		this.usersRepo = usersRepo;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserDto register(UserDto registerRequest) {
		UserDto resp = new UserDto();
	try {
		OurUser ourUser = new OurUser();
		ourUser.setUserName(registerRequest.getUserName());
		ourUser.setEmail(registerRequest.getEmail());
		ourUser.setRole(registerRequest.getRole());
		ourUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		
		OurUser userResult = usersRepo.save(ourUser);
		
		if(userResult.getId() != null) {
			resp.setOurUser(ourUser);
			resp.setMessage("OurUser save successfully.");
			resp.setStatusCode(200);
		}
		
	} catch (Exception e) {
		resp.setStatusCode(500);
		resp.setExpirationTime(e.getMessage());
		
	}
	
	
	return resp;
	
	}
	
	/*
	public ReqRes register(ReqRes registrationRequest) {
		ReqRes resp = new ReqRes();

		try {
			OurUsers ourUser = new OurUsers();
			ourUser.setEmail(registrationRequest.getEmail());
			ourUser.setCity(registrationRequest.getCity());
			ourUser.setRole(registrationRequest.getRole());
			ourUser.setName(registrationRequest.getName());
			ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
			OurUsers ourUsersResult = usersRepo.save(ourUser);
			if (ourUsersResult.getId() > 0) {
				resp.setOurUsers((ourUsersResult));
				resp.setMessage("OurUser Saved Successfully");
				resp.setStatusCode(200);
			}

		} catch (Exception e) {
			resp.setStatusCode(500);
			resp.setError(e.getMessage());
		}
		return resp;
	}
	*/
	
	
	

}
