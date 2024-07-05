package org.teadev.tunein.constants;

import org.teadev.tunein.entities.enums.RoleType;

import java.util.Map;

public class Constants {
    
    public static Map<RoleType, String> ROLE_MAP = Map.of(
            RoleType.USER, "User",
            RoleType.ADMIN, "Admin",
            RoleType.SUPER_ADMIN, "SuperAdmin"
    );
    
}
