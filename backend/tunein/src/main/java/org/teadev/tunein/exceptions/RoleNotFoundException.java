package org.teadev.tunein.exceptions;

public class RoleNotFoundException extends RuntimeException {
    
    public RoleNotFoundException(String roleNotFound) {
        super(roleNotFound);
    }
    
}
