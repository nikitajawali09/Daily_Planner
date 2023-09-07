package com.usermanagement.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.dto.TodoDto;
import com.usermanagement.model.TodoModel;
import com.usermanagement.model.User;
import com.usermanagement.service.TodoService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@Controller
@RequestMapping("/view")
@AllArgsConstructor
public class ViewController {

	private final TodoService todoService;

	// http://localhost:8080/view/hello
	@RequestMapping("/hello")
	public String helloWorld(Model model) {
		model.addAttribute("message", "Hello !");
		return "hello";
	}

	// http://localhost:8080/view/todos
	@RequestMapping("/todos")
	public String todos(Model model) {

		List<TodoDto> todos = todoService.getAllTodos();

		model.addAttribute("todos", todos);
		return "todos.html";
	}

	@RequestMapping("/variable-expression")
	public String variableExpression(Model model) {
		User user = new User("Ramesh", "ramesh@gmail.com", "ADMIN", "Male");
		model.addAttribute("user", user);
		return "variable-expression";
	}

	@GetMapping("/selection-expression")
	public String selectionExpression(Model model) {
		User user = new User("Ramesh", "ramesh@gmail.com", "ADMIN", "Male");
		model.addAttribute("user", user);
		return "selection-expression";
	}

	  @RequestMapping("/message-expression")
	    public String messageExpression(){
	        return "message-expression";
	    }

}
