package org.teadev.tunein.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeEntityResponseDto {
    
    private Long id;
    
    private Long postId;
    
    private String userId;
    
    private Long commentId;
    
}
