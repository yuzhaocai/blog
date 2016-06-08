package com.class8.blog.exception;

@SuppressWarnings("serial")
public class PostNotFoundException extends RuntimeException {
	
	public PostNotFoundException(String message){
		super(message);
	}
	
	public PostNotFoundException(String message,Throwable ex){
		super(message,ex);
	}
}
