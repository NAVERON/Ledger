package ledgerserver.service;

import model.user.RolePermissions;
import model.user.RoleType;
import model.user.UserAcount;
import model.user.UserAndPermissionDTO;

public interface UserAcountService {

    // 根据用户标识id 获取用户 
    public UserAcount getUserByEmail(String email);
    public UserAcount getUserByPhone(String phone);
    public UserAcount getUserByIdentifier(String identifier); // 电话或邮箱 实现判断 
    public UserAcount getUserById(Long id);
    public UserAcount getUserAcountByIdentifierAndPassword(String identifier, String password);
    
    // 根据角色类型获取角色权限 
    public RolePermissions getRolePermissionByRoleType(RoleType roleType);
    public RolePermissions getRolePermissionByRoleId(Long roleId);
    
    public UserAndPermissionDTO getUserAndPermissions(String identifier, String password);
}









