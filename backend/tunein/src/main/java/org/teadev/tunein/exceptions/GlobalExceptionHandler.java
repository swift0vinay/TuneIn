package org.teadev.tunein.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.teadev.tunein.constants.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException exception) {
        HttpStatusCode statusCode;
        ProblemDetail problemDetail;
        
        if (exception instanceof BadCredentialsException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.BAD_CREDENTIALS);
        } else if (exception instanceof UsernameNotFoundException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.USER_NOT_FOUND);
        } else {
            statusCode = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.INTERNAL_SERVER_ERROR);
        }
        return problemDetail;
    }
    
}
