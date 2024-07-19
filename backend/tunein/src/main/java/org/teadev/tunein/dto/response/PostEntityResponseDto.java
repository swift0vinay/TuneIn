package org.teadev.tunein.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostEntityResponseDto {
    
    private Long id;
    
    private String userId;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private String body;
    
    private String files;
    
    private Long likeCount;
    
    private List<String> errors;
    
}
