package com.usermanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "UserDto Model Information"
)
public class UserDto {
    private Long id;
//    @Schema(
//            description = "User First Name"
//    )
    @NotEmpty(message = "User first name should not be null or empty")
    private String firstName;
    @NotEmpty(message = "User last name should not be null or empty")
    private String lastName;
    @NotEmpty(message = "User email should not be null or empty")
    @Email(message = "Email address should be valid")
    private String email;
    @NotEmpty(message = "User password should not be null or empty")
    private String password;
    @NotEmpty(message = "User confirm password should not be null or empty")
	private String confirmPassword;
    @NotEmpty(message = "User gender should not be null or empty")
	private String gender;
	private String profession;
    @NotEmpty(message = "User address should not be null or empty")
	private String address;
    @NotEmpty(message = "User name should not be null or empty")
	private String userName;
    
}

