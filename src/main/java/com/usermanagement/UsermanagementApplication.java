package com.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "DAILY PLANNER", description = "DAILY PLANNER DOCUMENTATION", version = "v1.0", contact = @Contact(name = "Nikita", email = "nikitajawali0@gmail.com"), license = @License(name = "Apache 2.0", url = "https://www.nikita.net/license")))
public class UsermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);

	}

}
