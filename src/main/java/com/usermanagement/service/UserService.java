package com.usermanagement.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ui.Model;

import com.usermanagement.dto.UserDto;
import com.usermanagement.entities.User;

public interface UserService {

	Map<String, Object> createUser(UserDto user, Model model);

	UserDto getUserById(Long userId);

	List<UserDto> getAllUsers();

	UserDto updateUser(UserDto user);

	void deleteUser(Long userId);

	void saveUser(UserDto userDto);

	User findUserByEmail(String email);

	List<UserDto> findAllUsers();

	User findUserByuserName(String userName);
}
