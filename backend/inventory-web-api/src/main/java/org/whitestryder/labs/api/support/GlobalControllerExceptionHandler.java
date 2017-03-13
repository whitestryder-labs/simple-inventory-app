package org.whitestryder.labs.api.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.whitestryder.labs.app.support.ApplicationConflictException;



@ControllerAdvice
class GlobalControllerExceptionHandler {
	
	
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(ApplicationConflictException.class)
    @ResponseBody
    public ErrorInfo handleConflict(HttpServletRequest req, Exception ex) {
    	return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

}
