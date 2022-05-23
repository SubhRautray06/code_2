package com.property.demo.exceptionhandler;


import org.springframework.stereotype.Component;


public class EmptyInputException extends RuntimeException{

    public EmptyInputException(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
    private static final long serialVersionUID = 1L;
    private String errorMessage;
    private String errorCode;

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }




}
