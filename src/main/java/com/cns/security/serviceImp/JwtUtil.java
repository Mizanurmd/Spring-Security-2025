package com.cns.security.serviceImp;


import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	private SecretKey Key;
	private static final long EXPIRATION_TIME = 86400000; // 24 hours

	public JwtUtil() {
	    String secretString = "ODQzNTY3ODkzNjk2OTc2NDUzMjc1OTc0NDMyNjk3UjYzNDk3NlI3Mzg0NjdUUjY3OFQzNDg2NVI2ODM0Ujg3NjNUNDc4Mzc4NjM3NjY0NTM4NzQ1NjczODY1NzgzNjc4NTQ4NzM1Njg3UjM=";
	    byte[] keyBytes = Base64.getDecoder().decode(secretString); 
	    this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
	}


	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(Key).compact();
	}

	public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder().claims(claims).subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(Key).compact();
	}

	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
		return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}

}
