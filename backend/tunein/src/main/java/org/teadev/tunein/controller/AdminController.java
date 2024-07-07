package org.teadev.tunein.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    UserService userService;
    
    @PostMapping
    @RolesAllowed("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<UserEntity> createAdmin(@RequestBody UserRegisterRequestDto dto) {
        return ResponseEntity.ok(userService.createAdmin(dto));
    }
    
    @GetMapping
    @RolesAllowed("ROLE_SUPER_ADMIN")
    public ResponseEntity<List<UserEntity>> listAdmins() {
        return ResponseEntity.ok(userService.findAllAdmins());
    }
    
}
