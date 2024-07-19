package org.teadev.tunein.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.response.ErrorResponse;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setTitle(exception.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getStatus())).body(errorResponse);
    }
    
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationExceptions(AccessDeniedException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setTitle(exception.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getStatus())).body(errorResponse);
    }
    
    
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> handleResourceNotFoundExceptions(ResourceNotFoundException customException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTitle(customException.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeExceptions(RuntimeException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTitle(exception.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getStatus())).body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalExceptions(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setTitle(ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE);
        errorResponse.setErrors(List.of(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getStatus())).body(errorResponse);
    }
    
}
