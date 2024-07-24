package org.teadev.tunein.exceptions;

public class JwtTokenException extends RuntimeException {
    
    public JwtTokenException() {
        super();
    }
    
    public JwtTokenException(String message) {
        super(message);
    }
    
}
