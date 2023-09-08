package com.usermanagement.serviceImpl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.usermanagement.constant.Constant;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	private final ModelMapper modelMapper;

	boolean isValid = true;

	@Override
	@Transactional
	public Map<String, Object> createUser(UserDto userDto) {
		log.info("Entering into UserServiceImpl :: createUser");
		Map<String, Object> response = new HashMap<>();
		try {
			if (userDto.getId() == null) {
				Optional<User> optionalEmail = userRepository.findByEmail(userDto.getEmail());

				if (optionalEmail.isPresent()) {
					isValid = false;
					response.put(Constant.FAILED, 0);
					response.put(Constant.MESSAGE, "Email-Id already exists :Already Registered ?");
					// added login.html form

				}

				Optional<User> optionalUserName = userRepository.findByUserName(userDto.getUserName());

				if (optionalUserName.isPresent()) {
					isValid = false;
					response.put(Constant.FAILED, 0);
					response.put(Constant.MESSAGE, "User-Name already exists :Try with different username");

				}
			}

			if (isValid) {
				response = saveUser(userDto);
				

				return response;
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put(Constant.FAILED, 0);
			response.put(Constant.MESSAGE, "SOMETHING_WENT_WRONG");
		}
		log.info("Exiting into UserServiceImpl :: createUser");
		return response;
	}

	@Transactional
	private Map<String, Object> saveUser(UserDto userDto) {
		Map<String, Object> response = new HashMap<>();
		User user = modelMapper.map(userDto, User.class);
		
		if(!userDto.getPassword().equals(user.getConfirmPassword())) {
			response.put(Constant.FAILED, 0);
			response.put(Constant.MESSAGE, "Password and Confirm Password are not same");
		}else {
		
		user.setCreatedDate(new Date());
		User savedUser = userRepository.save(user);

		UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
		response.put(Constant.DATA, savedUserDto);
		response.put(Constant.SUCCESS, 1);
		}
		return response;
	}

	@Override
	@Transactional
	public UserDto getUserById(Long userId) {
		User user = null;
		try {

			user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User with ", "id :", userId));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelMapper.map(user, UserDto.class);

	}

	@Override
	@Transactional
	public List<UserDto> getAllUsers() {
		List<User> users = null;
		try {
			users = userRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	@Transactional
	public void deleteUser(Long userId) {
		try {
			userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

			userRepository.deleteById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
