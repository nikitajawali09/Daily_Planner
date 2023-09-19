package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entities.UsersRoles;

public interface UserRolesRepository extends JpaRepository<UsersRoles, Long>{

}
