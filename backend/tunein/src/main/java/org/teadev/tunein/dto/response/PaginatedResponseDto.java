package org.teadev.tunein.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginatedResponseDto {
    
    Integer count;
    
    List<?> results;
    
}
