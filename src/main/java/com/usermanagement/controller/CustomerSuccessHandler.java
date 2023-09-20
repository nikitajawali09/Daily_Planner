package com.usermanagement.controller;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
public class CustomerSuccessHandler implements AuthenticationSuccessHandler{


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		var authorities = authentication.getAuthorities();
		System.out.println("Auth:"+authorities);
		var roles = authorities.stream().map(r -> r.getAuthority()).findFirst();
		System.out.println("Roles:"+roles);
		if(roles.orElse("").equals("ROLE_ADMIN")) {
			response.sendRedirect("/users");		
		}else if(roles.orElse("").equals("ROLE_USER")) {
			response.sendRedirect("/todos/createTodo");	
		}else {
			response.sendRedirect("/error");
		}
		
	}

}
