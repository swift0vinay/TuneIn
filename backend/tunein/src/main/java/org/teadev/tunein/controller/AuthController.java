package org.teadev.tunein.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teadev.tunein.dto.request.UserLoginRequestDto;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserRegisterRequestDto dto) {
        UserEntity userEntity = authenticationService.register(dto);
        return ResponseEntity.ok(userEntity);
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestBody UserLoginRequestDto dto) {
        UserEntity userEntity = authenticationService.login(dto);
        return ResponseEntity.ok(userEntity);
    }
    
}
