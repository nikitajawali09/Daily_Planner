package com.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usermanagement.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

//	@Query("select t from Todo t where t.users_id=:id")
	//List<Todo> findUserId(@Param("id") Long id);
}

