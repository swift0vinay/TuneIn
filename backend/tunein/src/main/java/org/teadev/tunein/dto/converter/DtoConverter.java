package org.teadev.tunein.dto.converter;

import org.springframework.stereotype.Service;
import org.teadev.tunein.dto.response.PostEntityResponseDto;
import org.teadev.tunein.entities.PostEntity;

@Service
public class DtoConverter {
    
    public PostEntityResponseDto toDto(PostEntity post) {
        return PostEntityResponseDto
                .builder()
                .id(post.getId().toString())
                .userId(post.getUser().getId().toString())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .body(post.getBody())
                .files(post.getFiles())
                .build();
    }
    
}
