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

			// Date firstDate = todo.getTargetDate();
			// Date secondDate = todo.getCreatedDate();

			// long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
			// long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			// todo.setRemainingDaysToComplete(diff);
			Todo savedTodo = todoRepository.save(todo);
			TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
			return savedTodoDto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoDto;

	}

	@Override
	@Transactional
	public TodoDto getTodo(Long id) {

		try {
			Todo todo = todoRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));

			return modelMapper.map(todo, TodoDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public List<TodoDto> getAllTodos() {
		List<Todo> todos = null;
		try {
			todos = todoRepository.findAll();

			return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	@Transactional
	public void deleteTodo(Long id) {

		try {
			todoRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

			todoRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public TodoDto completeTodo(Long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

		todo.setCompleted(Boolean.TRUE);

		Todo updatedTodo = todoRepository.save(todo);

		return modelMapper.map(updatedTodo, TodoDto.class);
	}

	@Override
	@Transactional
	public TodoDto inCompleteTodo(Long id) {

		Todo todo = todoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

		todo.setCompleted(Boolean.FALSE);

		Todo updatedTodo = todoRepository.save(todo);

		return modelMapper.map(updatedTodo, TodoDto.class);
	}
}
