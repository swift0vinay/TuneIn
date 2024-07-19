package org.teadev.tunein.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.teadev.tunein.constants.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException exception) {
        exception.printStackTrace();
        HttpStatusCode statusCode;
        ProblemDetail problemDetail;
        
        if (exception instanceof BadCredentialsException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.BAD_CREDENTIALS_MESSAGE);
        } else if (exception instanceof UsernameNotFoundException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.USER_NOT_FOUND_MESSAGE);
        } else {
            statusCode = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return problemDetail;
    }
    
    
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAuthorizationExceptions(AccessDeniedException exception) {
        exception.printStackTrace();
        HttpStatusCode statusCode;
        ProblemDetail problemDetail;
        
        if (exception instanceof AuthorizationDeniedException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.ACCESS_DENIED_MESSAGE);
        } else {
            statusCode = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return problemDetail;
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeExceptions(RuntimeException exception) {
        exception.printStackTrace();
        ProblemDetail problemDetail;
        HttpStatusCode statusCode;
        if (exception instanceof FileUploadException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.FILE_UPLOAD_FAILURE_MESSAGE);
        } else if (exception instanceof RoleNotFoundException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.ROLE_NOT_FOUND_MESSAGE);
        } else if (exception instanceof UsernameNotFoundException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.USER_NOT_FOUND_MESSAGE);
        } else if (exception instanceof UserAlreadyPresentException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.USER_ALREADY_PRESENT_MESSAGE);
        } else if (exception instanceof PostNotFoundException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.POST_NOT_FOUND_MESSAGE);
        } else if (exception instanceof LikeNotFoundException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.LIKE_NOT_FOUND_MESSAGE);
        } else if (exception instanceof CommentNotFoundException) {
            statusCode = HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.COMMENT_NOT_FOUND_MESSAGE);
        } else {
            statusCode = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
            problemDetail = ProblemDetail.forStatusAndDetail(statusCode, exception.getMessage());
            problemDetail.setProperty("description", ErrorMessage.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return problemDetail;
    }
    
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalExceptions(Exception exception) {
        exception.printStackTrace();
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
    
}
