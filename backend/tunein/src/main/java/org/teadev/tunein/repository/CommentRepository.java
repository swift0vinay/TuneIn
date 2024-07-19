package org.teadev.tunein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teadev.tunein.entities.CommentEntity;
import org.teadev.tunein.entities.PostEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    
    List<CommentEntity> findCommentsByPost(PostEntity postEntity);
    
}
