package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
