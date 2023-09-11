package com.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "AGENDA OF THE DAY",
				description = "AGENDA OF THE DAY API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Nikita",
						email = "nikitajawali0@gmail.com",
						url = "https://www.nikita.net"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.nikita.net/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "AGENDA OF THE DAY Documentation",
				url = "https://www.nikita.net/user_management.html"
		)
)
public class UsermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);
		
		 PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	        System.out.println(passwordEncoder.encode("ramesh"));

	        System.out.println(passwordEncoder.encode("admin"));
	}

}
