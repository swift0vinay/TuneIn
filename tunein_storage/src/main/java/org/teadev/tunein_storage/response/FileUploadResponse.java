package org.teadev.tunein_storage.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class FileUploadResponse {
    
    private String id;
    
    private Date createdAt;
    
    private String url;
    
}
