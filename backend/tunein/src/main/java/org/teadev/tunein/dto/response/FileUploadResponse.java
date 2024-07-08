package org.teadev.tunein.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@ToString
public class FileUploadResponse implements Serializable {
    
    private String id;
    
    private Date createdAt;
    
    private String url;
    
}
