package com.portal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.portal.exception.NoContainerException;
import com.portal.exception.ParameterException;
import com.portal.exception.ServiceException;
/**
 * Class ExceptionController
 * @author santhoshkumardurairaj
 *
 */
@ControllerAdvice
public class ExceptionController {

	/**
	 * Handle Service Exception
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = ServiceException.class)
	public ResponseEntity<Object> exception(ServiceException exception) {
		return new ResponseEntity<>("Sorry there was a problem processing your request", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Handle ParamException
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = ParameterException.class)
	public ResponseEntity<Object> exception(ParameterException exception) {
		return new ResponseEntity<>("Please check the Input", HttpStatus.PARTIAL_CONTENT);
	}
	
	/**
	 * Handle NoContainerException
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = NoContainerException.class)
	public ResponseEntity<Object> exception(NoContainerException exception) {
		return new ResponseEntity<>("NO Container available in yard to book", HttpStatus.PARTIAL_CONTENT);
	}
}
