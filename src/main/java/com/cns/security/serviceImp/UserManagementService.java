package com.cns.security.serviceImp;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

			if (userResult.getId() != null) {
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

	public UserDto login(UserDto loginReq) {
		UserDto resp = new UserDto();
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginReq.getUserName(), loginReq.getPassword()));
			var user = usersRepo.findByUserName(loginReq.getUserName()).orElseThrow();
			var jwt = jwtUtil.generateToken(user);
			resp.setToken(jwt);
			resp.setRole(user.getRole());
			resp.setExpirationTime("24Hrs");
			resp.setMessage("Successfully Logged In");
			resp.setStatusCode(200);

		} catch (Exception e) {
			resp.setStatusCode(500);
			resp.setMessage(e.getMessage());
		}
		return resp;

	}
	
	/*
	 public ReqRes refreshToken(ReqRes refreshTokenReqiest) {
		ReqRes response = new ReqRes();
		try {
			String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
			OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
			if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
				var jwt = jwtUtils.generateToken(users);
				response.setStatusCode(200);
				response.setToken(jwt);
				response.setRefreshToken(refreshTokenReqiest.getToken());
				response.setExpirationTime("24Hr");
				response.setMessage("Successfully Refreshed Token");
			}
			response.setStatusCode(200);
			return response;

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	 
	 */
	
	public UserDto getAllUser() {
		UserDto resp = new UserDto();
		try {
			List<OurUser> allUsers = usersRepo.findAll();
			if(!allUsers.isEmpty()) {
				resp.setUserList(allUsers);
				resp.setStatusCode(200);
				resp.setMessage("All users get successfully.");
				
			}
			
		} catch (Exception e) {
			resp.setStatusCode(500);
			resp.setMessage("Error occurred: " + e.getMessage());
			
		}
		return resp;
	}
	
	
	/*
	public ReqRes getAllUsers() {
		ReqRes reqRes = new ReqRes();

		try {
			List<OurUsers> result = usersRepo.findAll();
			if (!result.isEmpty()) {
				reqRes.setOurUsersList(result);
				reqRes.setStatusCode(200);
				reqRes.setMessage("Successful");
			} else {
				reqRes.setStatusCode(404);
				reqRes.setMessage("No users found");
			}
			return reqRes;
		} catch (Exception e) {
			reqRes.setStatusCode(500);
			reqRes.setMessage("Error occurred: " + e.getMessage());
			return reqRes;
		}
	}
	 */

}
