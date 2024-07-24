package org.teadev.tunein.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teadev.tunein.dto.converter.DtoConverter;
import org.teadev.tunein.dto.request.UserLoginRequestDto;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.dto.response.UserLoginResponseDto;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.service.AuthenticationService;
import org.teadev.tunein.service.JwtService;
import org.teadev.tunein.service.TokenBlacklistService;
import org.teadev.tunein.utility.TuneInUtility;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private DtoConverter dtoConverter;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    
    private UserLoginResponseDto getUserLoginResponseDto(UserEntity userEntity) throws IllegalAccessException, InvocationTargetException {
        UserLoginResponseDto loginResponseDto = new UserLoginResponseDto();
        loginResponseDto.setToken(jwtService.generateToken(userEntity));
        BeanUtils.copyProperties(loginResponseDto, dtoConverter.toDto(userEntity));
        return loginResponseDto;
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserLoginResponseDto> register(@RequestBody UserRegisterRequestDto dto) throws InvocationTargetException, IllegalAccessException {
        UserEntity userEntity = authenticationService.register(dto);
        UserLoginResponseDto loginResponseDto = getUserLoginResponseDto(userEntity);
        return ResponseEntity.ok(loginResponseDto);
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto dto) throws InvocationTargetException, IllegalAccessException {
        UserEntity userEntity = authenticationService.login(dto);
        UserLoginResponseDto loginResponseDto = getUserLoginResponseDto(userEntity);
        return ResponseEntity.ok(loginResponseDto);
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        final String JWT_TOKEN = TuneInUtility.getJwtTokenFromHeaders(request);
        if (JWT_TOKEN != null) {
            tokenBlacklistService.blackListToken(JWT_TOKEN);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
}
