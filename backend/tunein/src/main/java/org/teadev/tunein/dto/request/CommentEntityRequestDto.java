package org.teadev.tunein.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentEntityRequestDto {
    
    private Long postId;
    
    private String userId;
    
    private String body;
    
}
