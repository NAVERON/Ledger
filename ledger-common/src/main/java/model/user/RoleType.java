package model.user;

import java.util.Arrays;
import java.util.Optional;

public enum RoleType { 
    ADMINISTRATOR, REGISTERED, ANONYMITY;
    
    public static RoleType Of(String roleTypeName) {
        Optional<RoleType> roleType = Arrays.asList(RoleType.values()).stream()
                .filter(role -> role.name().equals(roleTypeName))
                .findAny();
        
        return roleType.orElse(RoleType.ANONYMITY);
    }
}
