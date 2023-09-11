package com.usermanagement.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.usermanagement.constant.Constant;
import com.usermanagement.dto.TodoDto;
import com.usermanagement.dto.UserDto;
import com.usermanagement.entities.Todo;
import com.usermanagement.entities.User;
import com.usermanagement.model.UserForm;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class FormController {

//	private final UserRepository userRepository;
//	private final ModelMapper modelMapper;
//	
//	private final UserService userService;
//
//	@GetMapping("register")
//	public String userRegistrationPage(Model model) {
//		// Empty UserForm model object to store form data
//		UserForm userForm = new UserForm();
//		model.addAttribute("userForm", userForm);
//
//		List<String> listProfession = Arrays.asList("Student", "Experienced", "Fresher");
//		model.addAttribute("listProfession", listProfession);
//		return "register-form";
//	}
//
//	// handler method to handle user registration form submission request
//	@PostMapping("register/save")
//	public String submitForm(Model model, @ModelAttribute("userForm") UserForm userForm) {
//		model.addAttribute("userForm", userForm);
////		User user = new User();
////		user.setFirstName(userForm.getFirstName());
////		user.setLastName(userForm.getLastName());
////		user.setGender(userForm.getGender());
////		//String hashedPassword=DOMCryptoBinary.hashpw(user.getPassword(),DCrypt.gensalt(12));
////		user.setPassword(userForm.getPassword());
////		user.setProfession(userForm.getProfession());
////		user.setEmail(userForm.getEmail());
////		user.setUserName(userForm.getUserName());
////		user.setConfirmPassword(userForm.getConfirmPassword());
////		user.setAddress(userForm.getAddress());
//		
//		
//		User user = modelMapper.map(userForm, User.class);
//		UserDto savedTodoDto = modelMapper.map(user, UserDto.class);
//		Map<String, Object> response = userService.createUser(savedTodoDto,model);
//		
//		//response.put("userForm", userForm);
//		
//		//model.addAttribute("userForm", response);
//		//userRepository.save(user);
//		return "register-success";
//	}
//	
//	
//	@GetMapping("/loginPage")
//	public String loginPage(UserForm userForm) {
//		return "loginPage";
//	}
//	
//	
//	@PostMapping("/login")
//	public String loginProcess(@RequestParam("firstName") String firstName,
//			@RequestParam("password") String password) {
//		
//		User u = userRepository.findByFirstName(firstName);
//		return "todos";
//	}
//	
//	@GetMapping("/")
//	public String show() {
//		return "home";
//	}

}
