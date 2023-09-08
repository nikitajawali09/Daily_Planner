package com.usermanagement.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.usermanagement.dto.TodoDto;
import com.usermanagement.entities.Todo;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.repository.TodoRepository;
import com.usermanagement.service.TodoService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

	private TodoRepository todoRepository;

	private ModelMapper modelMapper;

	@Override
	@Transactional
	public TodoDto addTodo(TodoDto todoDto) {

		try {
			Todo todo = modelMapper.map(todoDto, Todo.class);
			todo.setCreatedDate(new Date());
			Todo savedTodo = todoRepository.save(todo);
			TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
			return savedTodoDto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoDto;

	}

	@Override
	public TodoDto getTodo(Long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));

		return modelMapper.map(todo, TodoDto.class);
	}

	@Override
	public List<TodoDto> getAllTodos() {

		List<Todo> todos = todoRepository.findAll();

		return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
	}

	@Override
	public TodoDto updateTodo(TodoDto todoDto, Long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setCompleted(todoDto.isCompleted());

		Todo updatedTodo = todoRepository.save(todo);

		return modelMapper.map(updatedTodo, TodoDto.class);
	}

	@Override
	public void deleteTodo(Long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

		todoRepository.deleteById(id);
	}

	@Override
	public TodoDto completeTodo(Long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

		todo.setCompleted(Boolean.TRUE);

		Todo updatedTodo = todoRepository.save(todo);

		return modelMapper.map(updatedTodo, TodoDto.class);
	}

	@Override
	public TodoDto inCompleteTodo(Long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

		todo.setCompleted(Boolean.FALSE);

		Todo updatedTodo = todoRepository.save(todo);

		return modelMapper.map(updatedTodo, TodoDto.class);
	}
}
