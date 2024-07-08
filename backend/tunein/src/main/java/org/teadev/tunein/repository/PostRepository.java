package org.teadev.tunein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teadev.tunein.entities.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

}
