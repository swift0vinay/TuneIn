package org.teadev.tunein.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.teadev.tunein.entities.CommentEntity;
import org.teadev.tunein.entities.PostEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    
    Optional<List<CommentEntity>> findCommentsByPost(PostEntity postEntity, Pageable pageable);
    
}
