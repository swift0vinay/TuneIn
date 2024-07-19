package org.teadev.tunein.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeEntityResponseDto {
    
    private Long id;
    
    private Long postId;
    
    private String userId;
    
    private Long commentId;
    
}
