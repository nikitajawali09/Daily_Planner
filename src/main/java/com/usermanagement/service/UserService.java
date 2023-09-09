package com.usermanagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.usermanagement.dto.UserDto;

public interface UserService {

	Map<String, Object> createUser(UserDto user,Model model);

	UserDto getUserById(Long userId);

	List<UserDto> getAllUsers();

	UserDto updateUser(UserDto user);

	void deleteUser(Long userId);
}
