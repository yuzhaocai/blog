package com.class8.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
	
	public PostNotFoundException(String message){
		super(message);
	}
	
	public PostNotFoundException(String message,Throwable ex){
		super(message,ex);
	}
}
