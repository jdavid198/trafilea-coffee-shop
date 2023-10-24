package com.trafilea.test.coffeeshop.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends Exception {
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 3323371286916855026L;
	
	private final HttpStatus code;
    private final String exception;
    
    public ApiException(HttpStatus code, String exception, Exception e) {
        super(e);
        this.code = code;
        this.exception = exception;
    }
    public ApiException(HttpStatus code, String exception) {
    	this.code = code;
    	this.exception = exception;
    }
    public ApiException(String exception, Exception e) {
        super(e);
        this.code = null;
        this.exception = exception;
    }

}
