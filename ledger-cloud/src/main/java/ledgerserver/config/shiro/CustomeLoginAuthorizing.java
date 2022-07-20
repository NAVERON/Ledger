package ledgerserver.config.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ledgerserver.service.impl.UserAcountServiceImpl;
import model.user.RolePermissions;
import model.user.UserAcount;

public class CustomeLoginAuthorizing extends AuthorizingRealm {
    
    private static final Logger log = LoggerFactory.getLogger(CustomeLoginAuthorizing.class);
    
    @Resource 
    private UserAcountServiceImpl userAcountServiceImpl;
    
    // 授权 // 规定 一个用户只能有1个角色, 一个角色设定多个权限 
    @Override 
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 从principals获取user 从user获取role 从role获取permission 
        UserAcount user = (UserAcount) principals.getPrimaryPrincipal();
        Long roleId = user.getRoleId();
        RolePermissions rolePermissions = this.userAcountServiceImpl.getRolePermissionByRoleId(roleId);
        List<String> permissions = rolePermissions.getPermissions();
        // 获取principal 授权特定的角色
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleSet = new HashSet() {{add(user.getRoleType().name());}};
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.addStringPermissions(permissions);
        // 添加权限 标记 
        
        return authorizationInfo;
    }
    
    // 认证 
    @Override 
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        
        UsernamePasswordToken verifyToken = (UsernamePasswordToken) token;
        String userName = verifyToken.getUsername();
        char[] password = verifyToken.getPassword();
        
        UserAcount user = this.userAcountServiceImpl.getUserByIdentifier(userName);
        if(user == null) {
            return null;
        }
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, password, userName);
        log.info("输出当前token --> {}, {}, {}", token.toString(), verifyToken.getUsername(), verifyToken.getPassword());
        
        return authenticationInfo;
    }

}









