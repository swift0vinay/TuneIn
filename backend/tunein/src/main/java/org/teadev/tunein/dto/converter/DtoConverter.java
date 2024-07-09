package org.teadev.tunein.dto.converter;

import org.springframework.stereotype.Service;
import org.teadev.tunein.dto.response.PostEntityResponseDto;
import org.teadev.tunein.dto.response.UserEntityResponseDto;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.entities.UserEntity;

import java.util.List;

@Service
public class DtoConverter {
    
    public UserEntityResponseDto toDto(UserEntity user) {
        if (user == null) {
            return null;
        }
        return UserEntityResponseDto
                .builder()
                .id(user.getId().toString())
                .name(user.getName())
                .email(user.getEmail())
                .handle(user.getHandle())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    
    public List<UserEntityResponseDto> toUserEntityListDto(List<UserEntity> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(this::toDto)
                .toList();
    }
    
    public PostEntityResponseDto toDto(PostEntity post) {
        if (post == null) {
            return null;
        }
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
    
    public List<PostEntityResponseDto> toPostEntityListDto(List<PostEntity> posts) {
        if (posts == null) {
            return null;
        }
        return posts.stream()
                .map(this::toDto)
                .toList();
    }
    
}
