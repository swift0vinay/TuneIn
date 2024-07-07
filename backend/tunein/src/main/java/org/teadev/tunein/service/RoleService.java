package org.teadev.tunein.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.entities.Role;
import org.teadev.tunein.entities.enums.RoleType;
import org.teadev.tunein.exceptions.RoleNotFoundException;
import org.teadev.tunein.repository.RoleRepository;

import java.util.Optional;

@Service
@Log4j2
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public Role validateAndFetchRole(RoleType roleType) {
        Optional<Role> role = roleRepository.findByRoleType(roleType);
        if (role.isEmpty()) {
            log.warn(ErrorMessage.ROLE_NOT_FOUND_MESSAGE);
            throw new RoleNotFoundException(ErrorMessage.ROLE_NOT_FOUND_MESSAGE);
        }
        return role.get();
    }
    
}
