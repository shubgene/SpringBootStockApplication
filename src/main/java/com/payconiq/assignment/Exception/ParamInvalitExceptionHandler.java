package com.payconiq.assignment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author User 
 * Exception class for parameter invalid exception
 */
@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class ParamInvalitExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParamInvalitExceptionHandler(String message) {
		super(message);
	}
}
