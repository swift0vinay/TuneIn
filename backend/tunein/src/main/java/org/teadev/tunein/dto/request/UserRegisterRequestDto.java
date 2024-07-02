package org.teadev.tunein.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterRequestDto {
    
    private String username;
    
    private String password;
    
}
