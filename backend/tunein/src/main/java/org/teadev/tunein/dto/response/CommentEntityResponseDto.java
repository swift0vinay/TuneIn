package org.teadev.tunein.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentEntityResponseDto {
    
    private Long id;
    
    private String body;
    
    private Long postId;
    
    private String userId;
    
    private Long likeCount;
    
}
