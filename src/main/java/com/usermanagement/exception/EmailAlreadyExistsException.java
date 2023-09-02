package com.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyExistsException extends RuntimeException{
    
	private static final long serialVersionUID = 3042871260927835181L;
	private String message;

    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
