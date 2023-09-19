package com.usermanagement.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "UserDto Model Information")
@ToString
public class UserDto {
	private Long id;
//    @Schema(
//            description = "User First Name"
//    )
	@NotEmpty(message = "User first name should not be null or empty")
	private String firstName;
	// @NotEmpty(message = "User last name should not be null or empty")
	// private String lastName;
	@NotEmpty(message = "User email should not be null or empty")
	@Email(message = "Email address should be valid")
	private String email;
	@NotEmpty(message = "User password should not be null or empty")
	@Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters")
	private String password;
	@Size(min = 5, max = 20, message = "Confirm Password must be between 10 and 20 characters")
	@NotEmpty(message = "User confirm password should not be null or empty")
	private String confirmPassword;
	@NotEmpty(message = "User gender should not be null or empty")
	private String gender;
	private String profession;
	@NotEmpty(message = "User address should not be null or empty")
	private String address;
//    @NotEmpty(message = "User name should not be null or empty")
//    @Size(min = 6, max = 20, message = "User name must be between 10 and 20 characters")
//	private String userName;

	private String title;
	private List<String> description;

}
