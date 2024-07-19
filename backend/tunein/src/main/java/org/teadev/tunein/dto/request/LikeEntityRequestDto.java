package org.teadev.tunein.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeEntityRequestDto {
    
    private Long postId;
    
    private Long commentId;
    
    private String userId;
    
}
