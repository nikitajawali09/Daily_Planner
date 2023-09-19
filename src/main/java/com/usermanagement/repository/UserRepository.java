package com.usermanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.usermanagement.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByName(String name);

	Boolean existsByEmail(String email);

}
