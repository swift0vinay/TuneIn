package org.teadev.tunein.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.entities.Role;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.entities.enums.RoleType;
import org.teadev.tunein.exceptions.ResourceNotFoundException;
import org.teadev.tunein.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    public UserEntity getUser(String id) {
        return userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND_MESSAGE));
    }
    
    public UserEntity createAdmin(UserRegisterRequestDto dto) {
        Role role = roleService.validateAndFetchRole(RoleType.ADMIN);
        UserEntity admin = UserEntity
                .builder()
                .email(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .build();
        return userRepository.save(admin);
    }
    
    public List<UserEntity> findAllAdmins() {
        Role role = roleService.validateAndFetchRole(RoleType.ADMIN);
        return userRepository.findAllByRole(role);
    }
    
    public List<UserEntity> findAllUsers() {
        Role role = roleService.validateAndFetchRole(RoleType.USER);
        return userRepository.findAllByRole(role);
    }
    
}
