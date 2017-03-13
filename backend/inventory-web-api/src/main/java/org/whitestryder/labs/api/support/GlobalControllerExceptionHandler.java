package org.whitestryder.labs.api.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.whitestryder.labs.app.support.ApplicationConflictException;
import org.whitestryder.labs.app.support.EntityNotFoundException;



@ControllerAdvice
class GlobalControllerExceptionHandler {
	
	
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(ApplicationConflictException.class)
    @ResponseBody
    public ErrorInfo handleConflict(HttpServletRequest req, Exception ex) {
    	return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

    
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ErrorInfo handleEntityNotFound(HttpServletRequest req, Exception ex) {
    	return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
    
}
