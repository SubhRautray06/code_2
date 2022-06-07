package com.paf.exercise.advice;

import com.paf.exercise.exception.ErrorResponse;
import com.paf.exercise.exception.ResourceAvailableException;
import com.paf.exercise.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException){
            ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),resourceNotFoundException.getErrorMessage(),resourceNotFoundException.getErrorTitle());
            return new ResponseEntity<> (error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceAvailableException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceAvailableException resourceAvailableException){
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), resourceAvailableException.getErrorMessage(),resourceAvailableException.getErrorTitle());
        return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);

    }


}
