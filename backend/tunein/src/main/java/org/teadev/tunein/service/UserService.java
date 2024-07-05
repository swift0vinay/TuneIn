package org.teadev.tunein.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.entities.Role;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.entities.enums.RoleType;
import org.teadev.tunein.repository.RoleRepository;
import org.teadev.tunein.repository.UserRepository;

import java.util.Optional;

@Service
@Log4j2
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserEntity createAdmin(UserRegisterRequestDto dto) {
        Optional<Role> role = roleRepository.findByRoleType(RoleType.ADMIN);
        if (role.isEmpty()) {
            log.warn("cannot find the required role");
            return null;
        }
        UserEntity admin = UserEntity
                .builder()
                .email(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role.get())
                .build();
        return userRepository.save(admin);
    }
    
}
