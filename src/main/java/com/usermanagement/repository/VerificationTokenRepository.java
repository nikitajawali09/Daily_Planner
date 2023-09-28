package com.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entities.VerificationToken;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	
	Optional<VerificationToken> findByToken(String token);

	
	// Optional<VerificationToken> findByToken(String token);

}
