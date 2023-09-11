package com.usermanagement.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.usermanagement.dto.UserDto;
import com.usermanagement.entities.User;
import com.usermanagement.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {
	
	Logger log = LoggerFactory.getLogger(AuthController.class);
	
	  private UserService userService;

	    public AuthController(UserService userService) {
	        this.userService = userService;
	    }
	    
	    // handler method to handle home page request
	    @GetMapping("/index")
	    public String home(){
	        return "index";
	    }
	    

	    // handler method to handle login request
	    @GetMapping("/login")
	    public String login(){
	        return "login";
	    }

	    // handler method to handle user registration form request
	    @GetMapping("/register")
	    public String showRegistrationForm(Model model){
	    	log.info("Entering into AuthController :: showRegistrationForm");
	        // create model object to store form data
	        UserDto user = new UserDto();
	        model.addAttribute("user", user);
	        log.info("Exiting into AuthController :: showRegistrationForm");
	        return "register";
	    }
	    
	    // handler method to handle user registration form submit request
	    @PostMapping("/register/save")
	    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
	                               BindingResult result,
	                               Model model){
	        try {
	        	log.info("Entering into AuthController :: registration");
				User existingEmail = userService.findUserByEmail(userDto.getEmail());

				if(existingEmail != null && existingEmail.getEmail() != null && !existingEmail.getEmail().isEmpty()){
				    result.rejectValue("email", null,
				            "There is already an account registered with the same email");
				}
				
				User existingUser = userService.findUserByuserName(userDto.getUserName());

				if(existingUser != null && existingUser.getUserName() != null && !existingUser.getUserName().isEmpty()){
				    result.rejectValue("userName", null,
				            "There is already an account registered with the same username. kindly try with different username");
				}
				
				
				if(userDto.getConfirmPassword() != null && userDto.getPassword() != null 
						){
					if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
				    result.rejectValue("password", null,
				            "Password and Confirm Password should be same");
					}
				}
				
				
				log.info("Entering into AuthController :: hasErrors");
				if(result.hasErrors()){
				    model.addAttribute("user", userDto);
				    return "/register";
				}
				log.info("Exiting into AuthController :: hasErrors");
				log.info("Entering into AuthController :: saveUser");
				userService.saveUser(userDto);
				log.info("Exiting into AuthController :: saveUser");
				  log.info("Exiting into AuthController :: registration");
				return "redirect:/register?success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }

}
