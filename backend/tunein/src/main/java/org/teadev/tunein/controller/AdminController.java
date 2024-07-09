package org.teadev.tunein.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.teadev.tunein.dto.converter.DtoConverter;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.dto.response.UserEntityResponseDto;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Log4j2
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DtoConverter dtoConverter;
    
    @PostMapping
    @RolesAllowed("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<UserEntityResponseDto> createAdmin(@RequestBody UserRegisterRequestDto dto) {
        UserEntity admin = userService.createAdmin(dto);
        return ResponseEntity.ok(dtoConverter.toDto(admin));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<UserEntityResponseDto>> listAdmins() {
        List<UserEntity> admins = userService.findAllAdmins();
        return ResponseEntity.ok(dtoConverter.toUserEntityListDto(admins));
    }
    
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<UserEntityResponseDto>> listUsers() {
        log.info(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        List<UserEntity> users = userService.findAllUsers();
        return ResponseEntity.ok(dtoConverter.toUserEntityListDto(users));
    }
    
}
