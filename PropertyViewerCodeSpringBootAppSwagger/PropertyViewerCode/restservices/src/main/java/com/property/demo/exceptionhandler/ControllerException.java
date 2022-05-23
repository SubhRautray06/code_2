package com.property.demo.exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerException{


	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<String> handleEmptyData(EmptyInputException emptyInputException){

		return new ResponseEntity<String>(emptyInputException.getErrorMessage(), HttpStatus.BAD_REQUEST);
	}
	
}
