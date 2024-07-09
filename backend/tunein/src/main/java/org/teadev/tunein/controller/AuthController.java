package org.teadev.tunein.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teadev.tunein.dto.converter.DtoConverter;
import org.teadev.tunein.dto.request.UserLoginRequestDto;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.dto.response.UserEntityResponseDto;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private DtoConverter dtoConverter;
    
    @PostMapping("/register")
    public ResponseEntity<UserEntityResponseDto> register(@RequestBody UserRegisterRequestDto dto) {
        UserEntity userEntity = authenticationService.register(dto);
        return ResponseEntity.ok(dtoConverter.toDto(userEntity));
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserEntityResponseDto> login(@RequestBody UserLoginRequestDto dto) {
        UserEntity userEntity = authenticationService.login(dto);
        return ResponseEntity.ok(dtoConverter.toDto(userEntity));
    }
    
}
