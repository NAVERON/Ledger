package ledgerserver.service;

import model.user.RolePermissions;
import model.user.RoleType;
import model.user.UserAcount;

public interface UserAcountService {

    // 根据用户标识id 获取用户 
    public UserAcount getUserByIdentifier(String identifier);  // 电话或邮箱 
    public UserAcount getUserById(String id);
    
    // 根据角色类型获取角色权限 
    public RolePermissions getRolePermissionByRoleType(RoleType roleType);
    public RolePermissions getRolePermissionByRoleId(Long roleId);
    
}









