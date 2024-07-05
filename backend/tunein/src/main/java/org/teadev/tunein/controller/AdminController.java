package org.teadev.tunein.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    UserService userService;
    
    @PostMapping
    @PreAuthorize("hasRole(SUPER_ADMIN)")
    public ResponseEntity<UserEntity> createAdmin(@RequestBody UserRegisterRequestDto dto) {
        return ResponseEntity.ok(userService.createAdmin(dto));
    }
    
}
