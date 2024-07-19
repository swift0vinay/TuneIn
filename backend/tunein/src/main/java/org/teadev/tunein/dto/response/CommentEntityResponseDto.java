package org.teadev.tunein.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentEntityResponseDto {
    
    private Long id;
    
    private String body;
    
    private Long postId;
    
    private String userId;
    
    private Long likeCount;
    
}
