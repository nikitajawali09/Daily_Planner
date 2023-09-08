package com.usermanagement.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.usermanagement.dto.UserDto;
import com.usermanagement.entities.User;
import com.usermanagement.exception.EmailAlreadyExistsException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.exception.UserNameAlreadyExistsException;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.UserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private ModelMapper modelMapper;

	@Override
	@Transactional
	public UserDto createUser(UserDto userDto) {

		try {
			Optional<User> optionalEmail = userRepository.findByEmail(userDto.getEmail());

			if (optionalEmail.isPresent()) {
				throw new EmailAlreadyExistsException("Email Already Exists for User");
			}

			Optional<User> optionalUserName = userRepository.findByUserName(userDto.getUserName());

			if (optionalUserName.isPresent()) {
				throw new UserNameAlreadyExistsException(
						"User Name Already Exists for User.Try with different username");
			}

			User user = modelMapper.map(userDto, User.class);
			user.setCreatedDate(new Date());
			User savedUser = userRepository.save(user);

			UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

			return savedUserDto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDto;
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
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		userRepository.deleteById(userId);
	}
}
