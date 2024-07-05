package org.teadev.tunein.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.teadev.tunein.constants.Constants;
import org.teadev.tunein.entities.Role;
import org.teadev.tunein.entities.enums.RoleType;
import org.teadev.tunein.repository.RoleRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (RoleType roleType : RoleType.values()) {
            if (roleRepository.findByRoleType(roleType).isPresent()) {
                continue;
            }
            Role role = new Role();
            role.setRoleType(roleType);
            role.setDescription(Constants.ROLE_MAP.get(roleType));
            roleRepository.save(role);
        }
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
    
    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
    
}
