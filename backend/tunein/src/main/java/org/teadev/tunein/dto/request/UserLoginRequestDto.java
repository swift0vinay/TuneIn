package org.teadev.tunein.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequestDto {
    
    private String username;
    
    private String password;
    
}
