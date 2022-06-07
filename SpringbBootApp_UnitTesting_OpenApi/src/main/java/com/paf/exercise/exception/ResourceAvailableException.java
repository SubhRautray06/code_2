package com.paf.exercise.exception;

import lombok.Data;

@Data
public class ResourceAvailableException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private final String errorMessage;
    private final String errorTitle;

    public ResourceAvailableException(String errorMessage,String errorTitle){
        super();
        this.errorMessage = errorMessage;
        this.errorTitle = errorTitle;
    }

}
