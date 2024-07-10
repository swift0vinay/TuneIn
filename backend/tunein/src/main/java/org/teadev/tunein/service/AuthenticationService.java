package org.teadev.tunein.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.request.UserLoginRequestDto;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.entities.Role;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.entities.enums.RoleType;
import org.teadev.tunein.exceptions.UserAlreadyPresentException;
import org.teadev.tunein.repository.UserRepository;

@Service
@Log4j2
public class AuthenticationService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private RoleService roleService;
    
    public UserEntity register(UserRegisterRequestDto dto) {
        if (userRepository.findByHandleOrEmail(dto.getUsername(), dto.getUsername()).isPresent()) {
            throw new UserAlreadyPresentException(ErrorMessage.USER_ALREADY_PRESENT_MESSAGE);
        }
        Role role = roleService.validateAndFetchRole(RoleType.USER);
        UserEntity userEntity = UserEntity.builder()
                .email(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .build();
        return userRepository.save(userEntity);
    }
    
    public UserEntity login(UserLoginRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        return userRepository
                .findByHandleOrEmail(dto.getUsername(), dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with email/handle as: " + dto.getUsername()));
    }
    
}
