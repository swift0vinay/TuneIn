package org.teadev.tunein.dto.converter;

import org.springframework.stereotype.Service;
import org.teadev.tunein.dto.response.PostEntityResponseDto;
import org.teadev.tunein.entities.PostEntity;

import java.util.List;

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
    
    public List<PostEntityResponseDto> toDto(List<PostEntity> posts) {
        if (posts == null) {
            return null;
        }
        return posts.stream()
                .map(this::toDto)
                .toList();
    }
    
}
