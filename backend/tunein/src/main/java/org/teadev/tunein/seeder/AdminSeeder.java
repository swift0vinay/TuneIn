package org.teadev.tunein.seeder;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.teadev.tunein.constants.ErrorMessage;
import org.teadev.tunein.dto.request.UserRegisterRequestDto;
import org.teadev.tunein.entities.Role;
import org.teadev.tunein.entities.UserEntity;
import org.teadev.tunein.entities.enums.RoleType;
import org.teadev.tunein.exceptions.ResourceNotFoundException;
import org.teadev.tunein.repository.RoleRepository;
import org.teadev.tunein.repository.UserRepository;

import java.util.Optional;

@Component
@Log4j2
@PropertySource(value = "classpath:super-admin-creds.properties")
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Value("${superadmin.name}")
    private String SUPER_ADMIN_NAME;
    
    @Value("${superadmin.email}")
    private String SUPER_ADMIN_EMAIL;
    
    @Value("${superadmin.password}")
    private String SUPER_ADMIN_PASSWORD;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createSuperAdmin();
    }
    
    private void createSuperAdmin() {
        UserRegisterRequestDto dto = UserRegisterRequestDto
                .builder()
                .username(SUPER_ADMIN_EMAIL)
                .password(SUPER_ADMIN_PASSWORD)
                .build();
        if (userRepository.findByEmail(dto.getUsername()).isPresent()) {
            log.info("superadmin already present");
            return;
        }
        
        Optional<Role> role = roleRepository.findByRoleType(RoleType.SUPER_ADMIN);
        if (role.isEmpty()) {
            log.warn(ErrorMessage.ROLE_NOT_FOUND_MESSAGE);
            throw new ResourceNotFoundException(ErrorMessage.ROLE_NOT_FOUND_MESSAGE);
        }
        
        log.info("Creating super admin");
        UserEntity userEntity = UserEntity
                .builder()
                .name(SUPER_ADMIN_NAME)
                .email(SUPER_ADMIN_EMAIL)
                .password(passwordEncoder.encode(SUPER_ADMIN_PASSWORD))
                .role(role.get())
                .build();
        
        userRepository.save(userEntity);
    }
    
    @Override
    public int getOrder() {
        return 1;
    }
    
    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
    
}
