package com.usermanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.usermanagement.dto.TodoDto;
import com.usermanagement.dto.UserDto;
import com.usermanagement.entities.User;
import com.usermanagement.service.TodoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/todos")
@CrossOrigin("*")
public class TodoController {

	Logger log = LoggerFactory.getLogger(TodoController.class);

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}
	
	// handler method to handle user registration form request
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/createTodo")
    public String createTodo(Model model){
    	log.info("Entering into AuthController :: createTodo");
        // create model object to store form data
        TodoDto user = new TodoDto();
        model.addAttribute("user", user);
        log.info("Exiting into AuthController :: createTodo");
        return "createTodo";
    }
	
	   // handler method to handle user registration form submit request
	//@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/save")
    public String todo(@Valid @ModelAttribute("user") TodoDto todoDto,
                               BindingResult result,
                               Model model){
        try {
        	log.info("Entering into AuthController :: registration");
			

			log.info("Entering into AuthController :: hasErrors");
			if(result.hasErrors()){
			    model.addAttribute("user", todoDto);
			    return "/createTodo";
			}
			
			
			log.info("Exiting into AuthController :: hasErrors");
			log.info("Entering into AuthController :: saveUser");
			todoService.addTodo(todoDto);
			log.info("Exiting into AuthController :: saveUser");
			  log.info("Exiting into AuthController :: registration");
			return "redirect:/todos/createTodo?success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
//
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
//	@PostMapping("/addNewTodo")
//	public ResponseEntity<TodoDto> addNewTodo(@Valid @RequestBody TodoDto todoDto) {
//
//		log.info("Entering into TodoController :: addNewTodo");
//		TodoDto savedTodo = todoService.addTodo(todoDto);
//		log.info("Exiting into TodoController :: addNewTodo");
//		return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
//	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getTodoById/{id}")
	public ResponseEntity<TodoDto> getTodoById(@Valid @PathVariable("id") Long todoId) {
		log.info("Entering into TodoController :: getTodoById");
		TodoDto todoDto = todoService.getTodo(todoId);
		log.info("Exiting into TodoController :: getTodoById");
		return new ResponseEntity<>(todoDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getAllTodos")
	public ResponseEntity<List<TodoDto>> getAllTodos() {
		log.info("Entering into TodoController :: getAllTodos");
		List<TodoDto> todos = todoService.getAllTodos();
		log.info("Exiting into TodoController :: getAllTodos");
		return ResponseEntity.ok(todos);
	}

	// Build Update Todo REST API
//	@PutMapping("{id}")
//	public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long todoId) {
//		log.info("Entering into TodoController :: updateTodo");
//		TodoDto updatedTodo = todoService.updateTodo(todoDto, todoId);
//		log.info("Exiting into TodoController :: updateTodo");
//		return ResponseEntity.ok(updatedTodo);
//	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteTodoById/{id}")
	public ResponseEntity<String> deleteTodoById(@PathVariable("id") Long todoId) {
		log.info("Entering into TodoController :: deleteTodoById");
		todoService.deleteTodo(todoId);
		log.info("Exiting into TodoController :: deleteTodoById");
		return ResponseEntity.ok("Todo deleted successfully!.");
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PatchMapping("{id}/complete")
	public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId) {
		TodoDto updatedTodo = todoService.completeTodo(todoId);
		return ResponseEntity.ok(updatedTodo);
	}

	   @PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PatchMapping("{id}/in-complete")
	public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long todoId) {
		TodoDto updatedTodo = todoService.inCompleteTodo(todoId);
		return ResponseEntity.ok(updatedTodo);
	}

}
