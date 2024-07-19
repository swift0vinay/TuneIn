package org.teadev.tunein.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntityResponseDto {
    
    private String id;
    
    private String name;
    
    private String email;
    
    private String handle;
    
    private Date createdAt;
    
    private Date updatedAt;
    
}
