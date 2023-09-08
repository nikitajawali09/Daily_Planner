package com.usermanagement.service;

import java.util.List;
import java.util.Map;

import com.usermanagement.dto.UserDto;

public interface UserService {

	Map<String, Object> createUser(UserDto user);

	UserDto getUserById(Long userId);

	List<UserDto> getAllUsers();

	UserDto updateUser(UserDto user);

	void deleteUser(Long userId);
}
