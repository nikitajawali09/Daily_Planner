package com.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usermanagement.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	User findByFirstName(String firstName);

	@Query("select p from User p where p.userName=:userName")
	Optional<User> findByUserName(@Param("userName") String userName);

	Optional<User> findByuserName(String username);

	Boolean existsByEmail(String email);

	Optional<User> findByuserNameOrEmail(String username, String email);
}
