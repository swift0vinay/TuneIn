package org.teadev.tunein.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.request.CommentEntityRequestDto;
import org.teadev.tunein.entities.CommentEntity;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.exceptions.CommentNotFoundException;
import org.teadev.tunein.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    public CommentEntity getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(ErrorMessage.COMMENT_NOT_FOUND_MESSAGE));
    }
    
    public CommentEntity addComment(PostEntity post, UserEntity user, CommentEntityRequestDto request) {
        CommentEntity commentEntity = CommentEntity
                .builder()
                .post(post)
                .user(user)
                .body(request.getBody())
                .build();
        return commentRepository.save(commentEntity);
    }
    
    public void deleteComment(Long commentId) {
        CommentEntity commentEntity = getComment(commentId);
        commentRepository.delete(commentEntity);
    }
    
    public List<CommentEntity> findCommentsByPost(PostEntity postEntity) {
        return commentRepository.findCommentsByPost(postEntity);
    }
    
    
}
