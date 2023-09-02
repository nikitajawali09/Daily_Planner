package com.usermanagement.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.usermanagement.dto.UserDto;
import com.usermanagement.entities.User;
import com.usermanagement.exception.EmailAlreadyExistsException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.UserService;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {

		Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

		if (optionalUser.isPresent()) {
			throw new EmailAlreadyExistsException("Email Already Exists for User");
		}

		User user = modelMapper.map(userDto, User.class);

		User savedUser = userRepository.save(user);

		UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

		return savedUserDto;
	}

	@Override
	public UserDto getUserById(Long userId) {
//		Optional<User> optionalUser = userRepository.findById(userId);
//		User user = optionalUser.get();

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		return modelMapper.map(user, UserDto.class);

	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
	}

	@Override
	public UserDto updateUser(UserDto user) {

		User existingUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		User updatedUser = userRepository.save(existingUser);

		return modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public void deleteUser(Long userId) {
		 User existingUser = userRepository.findById(userId).orElseThrow(
	                () -> new ResourceNotFoundException("User", "id", userId)
	        );

	        userRepository.deleteById(userId);
	}
}
