package com.usermanagement.service;

import java.util.Optional;

import com.usermanagement.entities.User;
import com.usermanagement.entities.VerificationToken;

public interface IVerificationTokenService {
	
	String validateToken(String token);
	
	void saveVerificationTokenForUser(User user,String token);
	
	Optional<VerificationToken> findByToken(String token);

}
