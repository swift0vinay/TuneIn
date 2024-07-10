package org.teadev.tunein.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnlikeRequestDto {
    
    private String postId;
    
    private String userId;
    
}
