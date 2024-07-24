package org.teadev.tunein.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntityResponseDto {
    
    private String id;
    
    private String name;
    
    private String email;
    
    private String handle;
    
    private Date createdAt;
    
    private Date updatedAt;
    
}
