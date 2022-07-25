package model.user;

import java.util.Arrays;
import java.util.Optional;


public enum RoleType { 
    ANONYMITY, // 等级级从低到高顺序 
    REGISTERED, 
    ADMINISTRATOR 
    ;
    
    public Boolean isBiggerThan(RoleType another) {
        return this.ordinal() > another.ordinal();
    }
    
    public static RoleType Of(String roleTypeName) {
        Optional<RoleType> roleType = Arrays.asList(RoleType.values()).stream()
                .filter(role -> role.name().equals(roleTypeName.toUpperCase()))
                .findAny();
        
        return roleType.orElse(RoleType.ANONYMITY);
    }
}
