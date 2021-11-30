/**
 * (c) Copyright 1998-2021, ANS. All rights reserved.
 */
package fr.ans.psc.delegate.impl;

import fr.ans.psc.delegate.impl.exception.PscRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//https://www.baeldung.com/exception-handling-for-rest-with-spring

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	  @ExceptionHandler(value 
		      = { PscRequestException.class})
		    protected ResponseEntity<Object> handleConflict(
		      RuntimeException ex, WebRequest request) {	      
		        PscRequestException except = (PscRequestException)ex;
		        return handleExceptionInternal(ex, except.getErreur(), 
		          new HttpHeaders(), except.getStatusARetourner(), request);
		    }
}
