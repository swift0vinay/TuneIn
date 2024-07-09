package org.teadev.tunein.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeRequestDto {
    
    private String postId;
    
    private String userId;
    
}
