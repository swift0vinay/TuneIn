package org.teadev.tunein.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserEntityResponseDto {
    
    private String id;
    
    private String name;
    
    private String email;
    
    private String handle;
    
    private Date createdAt;
    
    private Date updatedAt;
    
}
