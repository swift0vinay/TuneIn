package org.teadev.tunein.dto.converter;

import org.springframework.stereotype.Service;
import org.teadev.tunein.dto.response.CommentEntityResponseDto;
import org.teadev.tunein.dto.response.LikeEntityResponseDto;
import org.teadev.tunein.dto.response.PostEntityResponseDto;
import org.teadev.tunein.dto.response.UserEntityResponseDto;
import org.teadev.tunein.entities.CommentEntity;
import org.teadev.tunein.entities.LikeEntity;
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
                .id(post.getId())
                .userId(post.getUser().getId().toString())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .body(post.getBody())
                .files(post.getFiles())
                .likeCount(post.getLikeCount())
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
    
    public LikeEntityResponseDto toDto(LikeEntity likeEntity) {
        if (likeEntity == null) {
            return null;
        }
        return LikeEntityResponseDto.builder()
                .id(likeEntity.getId())
                .userId(likeEntity.getUser().getId().toString())
                .postId(likeEntity.getPost().getId())
                .commentId(likeEntity.getComment() == null ? null : likeEntity.getComment().getId())
                .build();
    }
    
    public CommentEntityResponseDto toDto(CommentEntity commentEntity) {
        if (commentEntity == null) {
            return null;
        }
        return CommentEntityResponseDto.builder()
                .id(commentEntity.getId())
                .postId(commentEntity.getPost().getId())
                .userId(commentEntity.getUser().getId().toString())
                .body(commentEntity.getBody())
                .likeCount(commentEntity.getLikeCount())
                .build();
    }
    
    public List<CommentEntityResponseDto> toCommentEntityListDto(List<CommentEntity> comments) {
        if (comments == null) {
            return null;
        }
        return comments.stream()
                .map(this::toDto)
                .toList();
    }
    
}
