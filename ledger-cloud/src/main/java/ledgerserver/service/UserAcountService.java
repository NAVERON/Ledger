package ledgerserver.service;

import java.util.List;

import model.UserIdentifierTypeEnum;
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
    
    // 获取用户基本信息和权限信息
    public UserAndPermissionDTO getUserAndPermissions(String identifier, String password);
    
    // 注册新用户 
    public UserAcount registUser(UserAcount user);
    public UserAcount registUser(UserIdentifierTypeEnum userIdentifier, String password);
    public UserAcount registUser(String identifier, String password);
    public UserAcount registUser(String identifier, String password, String roleType);
    public UserAcount activeUser(UserIdentifierTypeEnum userIdentifier, String code);
    // 删除用户账号信息 
    public UserAcount deleteUser(Long id);
    public UserAcount deleteUser(String identifier);  // 根据标识删除 邮箱或电话 其他删除不成功 
    
    // 更新用户信息 
    public UserAcount updateUserEmail(Long userId, String email);
    public UserAcount updateUserPhone(Long userId, String phone);
    public UserAcount updateUserName(Long userId, String userName);
    
    /**
     * 关于权限表的查询和修改操作 
     * 
     */
    // 根据角色类型获取角色权限 
    public RolePermissions getRolePermissionByRoleType(RoleType roleType);
    public RolePermissions getRolePermissionByRoleId(Long roleId);
    
    public RolePermissions addRolePermissions(RoleType roleType, List<String> permissions);
    public RolePermissions addRolePermissions(RoleType roleType, String permissions);
    public RolePermissions addRolePermissions(RoleType roleType, List<String> permissions, String description);
    
}









