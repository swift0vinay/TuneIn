package org.teadev.tunein.dto.request;


import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class PostEntityRequestDto {
    
    private String userId;
    
    private String body;
    
    private List<MultipartFile> files;
    
}
