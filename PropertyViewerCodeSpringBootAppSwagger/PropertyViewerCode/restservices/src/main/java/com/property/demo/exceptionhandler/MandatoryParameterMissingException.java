package com.property.demo.exceptionhandler;

public class MandatoryParameterMissingException extends Exception  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MandatoryParameterMissingException() {
		super();
	}
	
	public MandatoryParameterMissingException(final String message) {
		super(message);
	}
	public MandatoryParameterMissingException(final Throwable cause) {
		super(cause);
	}
	
	public MandatoryParameterMissingException(final String message , final Throwable cause) {
		super(message,cause);
	}
}
