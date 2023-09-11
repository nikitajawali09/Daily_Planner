package com.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usermanagement.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByName(String name);

	@Query("select p from User p where p.userName=:userName")
	Optional<User> findByUserName(@Param("userName") String userName);

	User findByuserName(String username);

	Boolean existsByEmail(String email);

	Optional<User> findByuserNameOrEmail(String username, String email);
}
