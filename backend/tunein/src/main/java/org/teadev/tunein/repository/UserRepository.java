package org.teadev.tunein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teadev.tunein.entities.Role;
import org.teadev.tunein.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    
    public Optional<UserEntity> findByHandle(String handle);
    
    public Optional<UserEntity> findByEmail(String email);
    
    public Optional<UserEntity> findByHandleOrEmail(String handle, String email);
    
    public List<UserEntity> findAllByRole(Role role);
    
}
