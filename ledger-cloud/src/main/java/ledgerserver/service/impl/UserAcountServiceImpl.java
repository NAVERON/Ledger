package ledgerserver.service.impl;


import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ledgerserver.jpadao.RolePermissionRepository;
import ledgerserver.jpadao.UserAcountRepository;
import ledgerserver.service.UserAcountService;
import model.UserIdentifierTypeEnum;
import model.user.RolePermissions;
import model.user.RoleType;
import model.user.UserAcount;
import model.user.UserAndPermissionDTO;

@Service 
@Primary 
public class UserAcountServiceImpl implements UserAcountService {

    private static final Logger log = LoggerFactory.getLogger(UserAcountServiceImpl.class);
    @Resource 
    private UserAcountRepository userAcountRepository;
    @Resource 
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public UserAcount getUserByEmail(String email) {
        Optional<UserAcount> user = this.userAcountRepository.findByEmailAddress(email);
        return user.orElse(null);
    }

    @Override
    public UserAcount getUserByPhone(String phone) {
        Optional<UserAcount> user = this.userAcountRepository.findByPhoneNumber(phone);
        return user.orElse(null);
    }

    @Override
    public UserAcount getUserByIdentifier(String identifier) {
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.of(identifier);
        
        if(userIdentifierType.getMarkName().equals(UserIdentifierTypeEnum.EMAIL.getMarkName())) {
            return this.getUserByEmail(identifier);
        }
        if(userIdentifierType.getMarkName().equals(UserIdentifierTypeEnum.PHONE.getMarkName())) {
            return this.getUserByPhone(identifier);
        }
        
        return null;
    }
    
    @Override 
    public UserAcount getUserAcountByIdentifierAndPassword(String identifier, String password) {
        Optional<UserAcount> user = this.userAcountRepository.findByIdentifierAndPassword(identifier, password);
        return user.orElse(null);
    }

    @Override 
    public UserAcount getUserById(Long id) {
        Optional<UserAcount> user = this.userAcountRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public RolePermissions getRolePermissionByRoleType(RoleType roleType) {
        Optional<RolePermissions> rolePermissions = this.rolePermissionRepository.findByRoleType(roleType);
        return rolePermissions.orElse(null);
    }

    @Override
    public RolePermissions getRolePermissionByRoleId(Long roleId) {
        Optional<RolePermissions> rolePermissions = this.rolePermissionRepository.findByRoleId(roleId);
        return rolePermissions.orElse(null);
    }

    @Override
    public UserAndPermissionDTO getUserAndPermissions(String identifier, String password) {
        
        UserAcount user = this.getUserAcountByIdentifierAndPassword(identifier, password);
        if(user == null) {
            return null;
        }
        RolePermissions rolePermissions = this.getRolePermissionByRoleId(user.getRoleId());
        
        UserAndPermissionDTO userAndPermissionDTO = UserAndPermissionDTO.createBuilder()
                .id(user.getId())
                .userName(user.getUserName())
                .emailAddress(user.getEmailAddress())
                .phoneNumber(user.getPhoneNumber())
                .roleType(user.getRoleType())
                .permissions(rolePermissions.getPermissions())
                .build();
        
        return userAndPermissionDTO;
    }

    /**
     * 1 检查是否已经存在用户 
     * 2 存在直接返回用户 不存在进入注册流程 
     * 3 创建用户并保存 返回 
     */
    @Override
    public UserAcount registUser(String identifier, String password) {
        UserAcount userExist = this.getUserAcountByIdentifierAndPassword(identifier, password);
        if(userExist != null) {
            log.warn("用户已经存在 重复创建/请重新使用id 或使用此id登录 !");
            return userExist;
        }
        UserAcount createdUser = this.userAcountRepository.save(new UserAcount(identifier, password));
        
        return createdUser;
    }

    @Override
    public UserAcount registUser(String identifier, String password, String roleType) {
        UserAcount userExist = this.getUserAcountByIdentifierAndPassword(identifier, password);
        if(userExist != null) {
            log.warn("用户已经存在 重复创建/请重新使用id 或使用此id登录 !");
            return userExist;
        }
        RoleType roleTypeEnum = RoleType.Of(roleType);
        UserAcount createdUser = this.userAcountRepository.save(new UserAcount(identifier, password, roleTypeEnum));

        return createdUser;
    }

    /**
     * 删除用户 
     * 1 先判断是否存在这个用户 
     * 2 存在 执行删除 
     * 3 不存在 直接返回空对象 
     */
    @Override
    public UserAcount deleteUser(Long id) {
        Optional<UserAcount> user = this.userAcountRepository.findById(id);
        if(user.isPresent()) {
            log.warn("user exists ! deleting ...");
            this.userAcountRepository.deleteById(id);
            return user.get();
        }
        
        log.info("用户不存在, 直接返回");
        return null;
    }

    @Override
    public UserAcount deleteUser(String identifier) {
        UserAcount user = this.getUserByIdentifier(identifier);
        if(user != null) {
            log.warn("用户存在,执行删除操作");
            this.userAcountRepository.deleteById(user.getId());
            return user;
        }
        
        log.info("用户不存在");
        return null;
    }

    @Override
    public UserAcount updateUserEmail(Long userId, String email) {
        Optional<UserAcount> user = this.userAcountRepository.findById(userId);
        if(user.isPresent()) {
            UserAcount updatedUser = user.get();
            updatedUser.setEmailAddress(email);
            updatedUser = this.userAcountRepository.save(updatedUser);
            
            return updatedUser;
        }
        
        log.warn("用户不存在, 更改无效");
        return null;
    }

    @Override
    public UserAcount updateUserPhone(Long userId, String phone) {
        Optional<UserAcount> user = this.userAcountRepository.findById(userId);
        if(user.isPresent()) {
            UserAcount updatedUser = user.get();
            updatedUser.setPhoneNumber(phone);
            updatedUser = this.userAcountRepository.save(updatedUser);
            
            return updatedUser;
        }
        
        log.warn("用户不存在, 更改无效");
        return null;
    }

    @Override
    public UserAcount updateUserName(Long userId, String userName) {
        Optional<UserAcount> user = this.userAcountRepository.findById(userId);
        if(user.isPresent()) {
            UserAcount updatedUser = user.get();
            updatedUser.setUserName(userName);
            updatedUser = this.userAcountRepository.save(updatedUser);
            
            return updatedUser;
        }
        
        log.warn("用户不存在, 更改无效");
        return null;
    }

    @Override
    public RolePermissions addRolePermissions(RoleType roleType, List<String> permissions) {
        RolePermissions rolePermissions = this.rolePermissionRepository.save(
                    new RolePermissions(roleType, permissions)
                );
        return rolePermissions;
    }

    @Override
    public RolePermissions addRolePermissions(RoleType roleType, String permissions) {
        RolePermissions rolePermissions = this.rolePermissionRepository.save(
                    new RolePermissions(roleType, permissions)
                );
        return rolePermissions;
    }

    @Override
    public RolePermissions addRolePermissions(RoleType roleType, List<String> permissions, String description) {
        RolePermissions rolePermissions = this.rolePermissionRepository.save(
                    new RolePermissions(roleType, permissions, description)
                );
        return rolePermissions;
    }
    
    
}



