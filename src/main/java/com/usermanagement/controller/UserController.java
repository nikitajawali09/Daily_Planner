package com.usermanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.usermanagement.dto.UserDto;
import com.usermanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs for User Resource", description = "CRUD REST APIs - Create User, Update User, Get User, Get All Users, Delete User")
@RestController
@RequestMapping("api/users")
@CrossOrigin("*")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/createNewUser")
	public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody UserDto user,Model model) {

		Map<String, Object> response = new HashMap<>();
		log.info("Entering into UserController :: createUser");
		
		response = userService.createUser(user,model);
		
		log.info("Exiting into UserController :: createUser");
		return ResponseEntity.accepted().body(response);

	}

	@Operation(summary = "Get User By ID REST API", description = "Get User By ID REST API is used to get a single user from the database")
	@ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
		log.info("Entering into UserController :: getUserById");
		
		UserDto user = userService.getUserById(userId);
		
		log.info("Exiting into UserController :: getUserById");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}


	@Operation(summary = "Get All Users REST API", description = "Get All Users REST API is used to get a all the users from the database")
	@ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		log.info("Entering into UserController :: getAllUsers");
		
		List<UserDto> users = userService.getAllUsers();
		
		log.info("Exiting into UserController :: getAllUsers");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

//	// Build Update User REST API
//	@Operation(summary = "Update User REST API", description = "Update User REST API is used to update a particular user in the database")
//	@ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
//	// http://localhost:8080/api/users/1
//	@PutMapping("{id}")
//	public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto user) {
//		user.setId(userId);
//		UserDto updatedUser = userService.updateUser(user);
//
//		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//	}

	@Operation(summary = "Delete User REST API", description = "Delete User REST API is used to delete a particular user from the database")
	@ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
		log.info("Entering into UserController :: deleteUser");
		userService.deleteUser(userId);
		log.info("Exiting into UserController :: deleteUser");
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
	}
}
