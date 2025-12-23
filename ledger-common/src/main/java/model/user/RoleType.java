package model.user;

import java.util.Arrays;
import java.util.Optional;


public enum RoleType {  // 既然user中包含了role 和role_id 这里就应该封装id值 
    ANONYMITY, // 等级级从低到高顺序  应该提供一个管理角色和权限的接口 不使用enum 几个固定的角色,提供角色的可创建 这里暂时使用人工处理的办法 
    REGISTERED, 
    ADMINISTRATOR 
    ;
    
    public Boolean isBiggerOrEqual(RoleType another) {
        return this.ordinal() >= another.ordinal();  // ordinal 从 0 开始 
    }
    
    public static RoleType Of(String roleTypeName) {
        Optional<RoleType> roleType = Arrays.asList(RoleType.values()).stream()
                .filter(role -> role.name().equals(roleTypeName.toUpperCase()))
                .findAny();
        
        return roleType.orElse(RoleType.ANONYMITY);
    }
}
