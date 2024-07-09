package org.teadev.tunein.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PostEntityResponseDto {
    
    private String id;
    
    private String userId;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private String body;
    
    private String files;
    
    private Long likeCount;
    
}
