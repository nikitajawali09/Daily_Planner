package com.usermanagement.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.usermanagement.entities.User;
import com.usermanagement.model.UserForm;
import com.usermanagement.repository.UserRepository;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class FormController {

	private final UserRepository userRepository;

	@GetMapping("register")
	public String userRegistrationPage(Model model) {
		// Empty UserForm model object to store form data
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);

		List<String> listProfession = Arrays.asList("Student", "Experienced", "Fresher");
		model.addAttribute("listProfession", listProfession);
		return "register-form";
	}

	// handler method to handle user registration form submission request
	@PostMapping("register/save")
	public String submitForm(Model model, @ModelAttribute("userForm") UserForm userForm) {
		model.addAttribute("userForm", userForm);
		User user = new User();
		user.setFirstName(userForm.getFirstName());
		user.setLastName(userForm.getLastName());
		user.setGender(userForm.getGender());
		user.setPassword(userForm.getPassword());
		user.setProfession(userForm.getProfession());
		user.setEmail(userForm.getEmail());
		userRepository.save(user);
		return "register-success";
	}

}
