package com.payconiq.assignment.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @author User
 * Exception clss for stocknot found exception
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StockNotPresentExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StockNotPresentExceptionHandler(String message) {
		super(message);
	}
}
