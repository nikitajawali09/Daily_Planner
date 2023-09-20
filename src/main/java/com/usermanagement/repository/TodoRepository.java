package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usermanagement.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
