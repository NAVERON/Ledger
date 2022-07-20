package ledgerserver.service.impl;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ledgerserver.dao.RolePermissionRepository;
import ledgerserver.dao.UserAcountRepository;
import ledgerserver.service.UserAcountService;
import model.user.RolePermissions;
import model.user.RoleType;
import model.user.UserAcount;

@Service 
@Primary 
public class UserAcountServiceImpl implements UserAcountService {

    private static final Logger log = LoggerFactory.getLogger(UserAcountServiceImpl.class);
    
    @Resource 
    private UserAcountRepository userAcountRepository;
    @Resource 
    private RolePermissionRepository rolePermissionRepository;
    
    @Override
    public UserAcount getUserByIdentifier(String identifier) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public UserAcount getUserById(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RolePermissions getRolePermissionByRoleType(RoleType roleType) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RolePermissions getRolePermissionByRoleId(Long roleId) {
        // TODO Auto-generated method stub
        return null;
    }
    

    
    
}



