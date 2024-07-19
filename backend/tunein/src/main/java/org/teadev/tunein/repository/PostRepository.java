package org.teadev.tunein.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    
    Optional<List<PostEntity>> findPostByUser(UserEntity user, Pageable pageable);
    
}
