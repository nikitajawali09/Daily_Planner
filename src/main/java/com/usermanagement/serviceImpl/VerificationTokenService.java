package com.usermanagement.serviceImpl;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.usermanagement.entities.User;
import com.usermanagement.entities.VerificationToken;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.repository.VerificationTokenRepository;
import com.usermanagement.service.IVerificationTokenService;

@Service
public class VerificationTokenService implements IVerificationTokenService{
	
	private final VerificationTokenRepository tokenRepository;
	private final UserRepository userRepository;

	public VerificationTokenService(VerificationTokenRepository tokenRepository,UserRepository userRepository) {
		super();
		this.tokenRepository = tokenRepository;
		this.userRepository=userRepository;
	}

	@Override
	public String validateToken(String token) {
		
		Optional<VerificationToken> theToken = tokenRepository.findByToken(token);
		
		if(theToken.isEmpty()) {
			return "Invalid verification token";
		}
		User user = theToken.get().getUser();
		Calendar calendar = Calendar.getInstance();
		if(theToken.get().getExpirationTime().getTime()-calendar.getTime().getTime()<=0) {
			
			return "expired";
			
		}
		user.setEnabled(true);
		userRepository.save(user);
		
		return "valid";
	}

	@Override
	public void saveVerificationTokenForUser(User user, String token) {
		
		var verificationToken = new VerificationToken(token, user);
		tokenRepository.save(verificationToken);
		
	}

	@Override
	public Optional<VerificationToken> findByToken(String token) {
		
		return tokenRepository.findByToken(token);
	}

}
