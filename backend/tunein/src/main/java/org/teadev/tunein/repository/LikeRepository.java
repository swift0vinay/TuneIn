package org.teadev.tunein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teadev.tunein.entities.LikeEntity;
import org.teadev.tunein.entities.PostEntity;
import org.teadev.tunein.entities.UserEntity;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    
    Optional<LikeEntity> findByPostAndUser(PostEntity post, UserEntity user);
    
}
