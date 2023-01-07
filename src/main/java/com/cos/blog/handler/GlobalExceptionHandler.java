package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice //  Exception이 들어왔을때 이쪽으로 들어옴
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public String handleArgumentException(IllegalArgumentException e) {
		return "<h1>" +e.getMessage() +"</h1>";
	}
	

	
}
