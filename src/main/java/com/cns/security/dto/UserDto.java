package com.cns.security.dto;



import java.util.List;
import java.util.UUID;

import com.cns.security.entity.OurUser;
import com.cns.security.enumType.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown  = true)
public class UserDto {    
    private int statusCode;
	private String error;
	private String message;
	private String token;
	private String refreshToken;
	private String expirationTime;
	private String userName;
	private String email;
	private Role role;
	private String password;
	private OurUser ourUser;
	private List<OurUser> userList;

}
