package com.usermanagement.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usermanagement.dto.TodoDto;
import com.usermanagement.dto.UserDto;
import com.usermanagement.entities.User;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.TodoService;
import com.usermanagement.service.UserService;
import jakarta.validation.Valid;

@Controller
@CrossOrigin("*")
public class AuthController {

	Logger log = LoggerFactory.getLogger(AuthController.class);

	private UserService userService;
	private TodoService todoService;
	private UserDetailsService userDetailsService;

	public AuthController(UserService userService,TodoService todoService,UserDetailsService userDetailsService) {
		this.userService = userService;
		this.todoService=todoService;
		this.userDetailsService=userDetailsService;

	}
		
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user-view")
	public String userView(Model model,Principal principal) {
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		
		User user = userService.findUserByEmail(userDetails.getUsername());
		
		model.addAttribute("user", user);
		return "user-view";
	}

	// handler method to handle list of users
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/users")
	public String users(Model model) {

		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "users";
	}
	
	// handler method to handle edit student request
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/users/{id}/view")
	public String view(@PathVariable("id") Long id, Model model) {

	
		List<TodoDto> users = todoService.getUserTodoById(id);
		model.addAttribute("users", users);
		return "todo-view";
	}
	
	// handler method to handle edit student request
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
		@GetMapping("/users/{id}/userview")
		public String userview(@PathVariable("id") Long id, Model model) {

			
			List<TodoDto> users = todoService.getUserTodoById(id);
			model.addAttribute("users", users);
			return "user-todo";
		}

	// handler method to handle edit student request
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/users/{id}/edit")
	public String editStudent(@PathVariable("id") Long id, Model model) {

		UserDto user = userService.getStudentById(id);

		model.addAttribute("user", user);
		return "edit-user";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/editTodo/{id}")
	public String editTodo(@PathVariable("id") Long id, Model model) {

		TodoDto user = todoService.updateTodoById(id);

		model.addAttribute("user", user);
		return "edit-todo";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/todo/{id}")
	public String updateTodo(@PathVariable("id") Long id, @Valid @ModelAttribute("user") TodoDto userDto,
			BindingResult result, Model model) {

		System.out.println("Inside updatetodo");
//		if (result.hasErrors()) {
//			System.out.println("Inside updateStudent error:"+result.getErrorCount());
//			model.addAttribute("user", userDto);
//			return "edit-user";
//		}

		userDto.setId(id);
		todoService.updateTodo(userDto,id);
		return "redirect:/users";
	}
	

	// handler method to handle edit student form submit request
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/users/{id}")
	public String updateStudent(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDto userDto,
			BindingResult result, Model model) {

		System.out.println("Inside updateStudent");
//		if (result.hasErrors()) {
//			System.out.println("Inside updateStudent error:"+result.getErrorCount());
//			model.addAttribute("user", userDto);
//			return "edit-user";
//		}

		userDto.setId(id);
		userService.updateUser(userDto);
		return "redirect:/users";
	}

	// Handler method to handle delete student request
	@GetMapping("/users/{id}/delete")
	public String deleteStudent(@PathVariable("id") Long userId) {
		userService.deleteUser(userId);
		return "redirect:/users";
	}

}
