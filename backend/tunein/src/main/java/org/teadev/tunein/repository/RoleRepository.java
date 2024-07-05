package org.teadev.tunein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teadev.tunein.entities.Role;
import org.teadev.tunein.entities.enums.RoleType;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Optional<Role> findByRoleType(RoleType roleType);
    
}
