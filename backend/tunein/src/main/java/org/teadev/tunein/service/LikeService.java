package org.teadev.tunein.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.request.LikeEntityRequestDto;
import org.teadev.tunein.dto.request.UnlikeRequestDto;
import org.teadev.tunein.entities.CommentEntity;
import org.teadev.tunein.entities.LikeEntity;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.exceptions.ResourceNotFoundException;
import org.teadev.tunein.repository.LikeRepository;

import java.util.Optional;

@Service
public class LikeService {
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Transactional
    public LikeEntity likePost(PostEntity post, UserEntity user, LikeEntityRequestDto request) {
        // check if post is already liked by the same user
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findByPostAndUser(post, user);
        if (optionalLikeEntity.isPresent()) {
            return optionalLikeEntity.get();
        }
        
        LikeEntity likeEntity = LikeEntity
                .builder()
                .post(post)
                .user(user)
                .build();
        return likeRepository.save(likeEntity);
    }
    
    @Transactional
    public void unlikePost(PostEntity post, UserEntity user, UnlikeRequestDto request) {
        LikeEntity likeEntity = likeRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.LIKE_NOT_FOUND_MESSAGE));
        likeRepository.delete(likeEntity);
    }
    
    @Transactional
    public LikeEntity likeComment(PostEntity post, UserEntity user, CommentEntity comment, LikeEntityRequestDto request) {
        // check if comment is already liked by the same user
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findByPostAndUserAndComment(post, user, comment);
        if (optionalLikeEntity.isPresent()) {
            return optionalLikeEntity.get();
        }
        
        LikeEntity likeEntity = LikeEntity
                .builder()
                .post(post)
                .user(user)
                .comment(comment)
                .build();
        return likeRepository.save(likeEntity);
    }
    
    @Transactional
    public void unlikeComment(PostEntity post, UserEntity user, CommentEntity comment, UnlikeRequestDto request) {
        LikeEntity likeEntity = likeRepository.findByPostAndUserAndComment(post, user, comment)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.LIKE_NOT_FOUND_MESSAGE));
        likeRepository.delete(likeEntity);
    }
    
}
