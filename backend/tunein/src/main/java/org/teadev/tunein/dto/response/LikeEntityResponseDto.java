package org.teadev.tunein.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeEntityResponseDto {
    
    private Integer id;
    
    private Integer postId;
    
    private String userId;
    
    private Integer commentId;
    
}
