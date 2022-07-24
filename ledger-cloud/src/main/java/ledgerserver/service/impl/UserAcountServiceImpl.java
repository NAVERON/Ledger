package ledgerserver.service.impl;


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

    @Override
    public UserAcount registUser(String identifier, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAcount registUser(String identifier, String password, String roleType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAcount deleteUser(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAcount deleteUser(String identifier) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAcount updateUserEmail(Long userId, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAcount updateUserPhone(Long userId, String phoneNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserAcount updateUserName(Long userId, String userName) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}



