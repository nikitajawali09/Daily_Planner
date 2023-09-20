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
import com.usermanagement.dto.UserDto;
import com.usermanagement.entities.User;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.repository.UserRolesRepository;
import com.usermanagement.service.UserService;
import jakarta.validation.Valid;

@Controller
@CrossOrigin("*")
public class AuthController {
	
	Logger log = LoggerFactory.getLogger(AuthController.class);

	private UserService userService;
	private final UserRolesRepository userRolesRepo;
	private final UserRepository userRepo;
	  
	public AuthController(UserService userService, UserRolesRepository userRolesRepo, UserRepository userRepo) {
		this.userService = userService;
		this.userRolesRepo = userRolesRepo;
		this.userRepo = userRepo;

	}
	
	// handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
    	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	    
	    // handler method to handle home page request
	    @GetMapping("/index")
	    public String home(){
	        return "index";
	    }   
	    
	    @GetMapping("/login")
	    public String login(){
	        return "login";
	    }

	    // handler method to handle login request
//	    @GetMapping("/login")
//	    public String login(Model model){
//	    	UserDto user = new UserDto();
//	        model.addAttribute("user", user);
//	        System.out.println("User login :"+user);
//	        return "login";
//	    }
	    
//	    // handler method to handle login request
//	    @GetMapping("/adminlogin")
//	    public String adminlogin(Model model){
//	        return "adminlogin";
//	    }
	    
	  
	    
	   

	    
	    
	 // handler method to handle list of users
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    @GetMapping("/users")
	    public String users(Model model){
	    	
	        List<UserDto> users = userService.findAllUsers();	        
	        model.addAttribute("users", users);
	        return "users";
	        
	    }
	    
//		@PreAuthorize("hasAnyRole('ADMIN','USER')")
//		@GetMapping("/welcome")
//		public String welcome(Model model) {
//			List<UserDto> users = userService.findAllUsers();
//			model.addAttribute("users", users);
//			return "welcome";
//		}
		
		// handler method to handle edit student request
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    @PostMapping("/assignRole")
	    public String assignRole(@Valid @ModelAttribute("user") UserDto todoDto,
                BindingResult result,
                Model model){
	    	
	    	 System.out.println("User todo  :"+todoDto.getUsername());
	    	 
	    	 //todoDto.setPassword(passwordEncoder.encode(todoDto.getPassword()));
	    	 
	    	 //System.out.println("User todo password  :"+todoDto.get());
	    	 
	    	 User user = userRepo.findByUsername(todoDto.getUsername());
	        Long findByRoleId = userRolesRepo.findRoleId(user.getId());
	       
	        System.out.println("findByRoleId:"+findByRoleId);
	        
	        if (findByRoleId == 2) {
				System.out.println("Inside todos");
				return "redirect:/todos/createTodo";
			}
	        
			if (findByRoleId == 1) {
				System.out.println("Inside users");
				 return "redirect:/users";
			}
			
	        
//	      System.out.println("User:"+user);
//	        model.addAttribute("user", user);
			return "redirect:/users";
	        
	    }

	    // handler method to handle edit student request
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    @GetMapping("/users/{id}/edit")
	    public String editStudent(@PathVariable("id") Long id,
	                              Model model){
	    	
	        UserDto user = userService.getStudentById(id);
	        
	        
	      
	        model.addAttribute("user", user);
	        return "edit-user";
	    }
	 

	 // handler method to handle edit student form submit request
	    @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    @PostMapping("/users/{id}")
	    public String updateStudent(@PathVariable("id") Long id,
	                                @Valid @ModelAttribute("user") UserDto userDto,
	                                BindingResult result,
	                                Model model){
	    	System.out.println("Inside updateStudent");
	        if(result.hasErrors()){
	        	 model.addAttribute("user", userDto);
	            return "edit-user";
	        }
	        
	        userDto.setId(id);
	        userService.updateUser(userDto);
	        return "redirect:/users";
	    }
	    
	    
	    // Handler method to handle delete student request
	    @GetMapping("/users/{id}/delete")
	    public String deleteStudent(@PathVariable("id") Long userId){
	    	userService.deleteUser(userId);
	        return "redirect:/users";
	    }
	    
	    
	 
	    
}

