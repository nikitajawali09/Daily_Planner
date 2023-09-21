package com.usermanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.usermanagement.service.TodoService;
import com.usermanagement.service.UserService;
import jakarta.validation.Valid;

@Controller
@CrossOrigin("*")
public class AuthController {

	Logger log = LoggerFactory.getLogger(AuthController.class);

	private UserService userService;
	private TodoService todoService;

	public AuthController(UserService userService,TodoService todoService) {
		this.userService = userService;
		this.todoService=todoService;

	}

	// handler method to handle user registration form request
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {

		log.info("Entering into AuthController :: showRegistrationForm");

		UserDto user = new UserDto();
		model.addAttribute("user", user);

		log.info("Exiting into AuthController :: showRegistrationForm");
		return "register";
	}

	// handler method to handle user registration form submit request
	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {

		try {

			log.info("Entering into AuthController :: registration");
			User existingEmail = userService.findUserByEmail(userDto.getEmail());

			if (existingEmail != null && existingEmail.getEmail() != null && !existingEmail.getEmail().isEmpty()) {
				result.rejectValue("email", null, "There is already an account registered with the same email");
			}

			if (userDto.getConfirmPassword() != null && userDto.getPassword() != null) {
				if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
					result.rejectValue("password", null, "Password and Confirm Password should be same");
				}
			}

			log.info("Entering into AuthController :: hasErrors");
			if (result.hasErrors()) {
				model.addAttribute("user", userDto);
				return "/register";
			}

			userService.saveUser(userDto);

			log.info("Exiting into AuthController :: registration");
			return "redirect:/register?success";
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	// handler method to handle home page request
	@GetMapping("/index")
	public String home() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/contact")
	public String contact() {
		return "contact";
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
	@GetMapping("/users/{id}/edit")
	public String editStudent(@PathVariable("id") Long id, Model model) {

		UserDto user = userService.getStudentById(id);

		model.addAttribute("user", user);
		return "edit-user";
	}
	
	// handler method to handle edit student request
		@PreAuthorize("hasAnyRole('ADMIN','USER')")
		@GetMapping("/users/{id}/view")
		public String view(@PathVariable("id") Long id, Model model) {

			System.out.println("todo id:"+id);
			List<TodoDto> users = todoService.getUserTodoById(id);	
			model.addAttribute("users", users);
			return "todo-view";
		}

	// handler method to handle edit student form submit request
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/users/{id}")
	public String updateStudent(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDto userDto,
			BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("user", userDto);
			return "edit-user";
		}

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
