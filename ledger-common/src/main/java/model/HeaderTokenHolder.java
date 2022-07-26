package model;

import java.lang.reflect.InaccessibleObjectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.user.RoleType;
import model.user.UserAndPermissionDTO;
import utils.JWTUtils;

/**
 * 保存验证过程中的 header token 方便其他过程验证取出 
 * @author eron 
 * 
 */
public class HeaderTokenHolder {

    private static final Logger log = LoggerFactory.getLogger(HeaderTokenHolder.class);
    
    private static final ThreadLocal<String> authenticationToken = ThreadLocal.withInitial(() -> "");
    private static UserAndPermissionDTO user = null;
    
    public static void setHolderValue(String value) {
        log.info("Header Token Value ThreadLocal --> {}", value);
        HeaderTokenHolder.authenticationToken.set(value);
        HeaderTokenHolder.user = JWTUtils.getUser(HeaderTokenHolder.authenticationToken.get());  // PreProcessing 
        if(HeaderTokenHolder.user == null) {
            HeaderTokenHolder.authenticationToken.set("");  // reset 
            throw new IllegalArgumentException("Please set Token And Make Sure Token is Valid( user isValidToken() Function Check)");
        }
    }
    
    public static void clearHolderValue() {
        HeaderTokenHolder.authenticationToken.remove();
        HeaderTokenHolder.user = null;
    }
    
    public static String getHolderValue() {
        return HeaderTokenHolder.authenticationToken.get();
    }
    
    @Deprecated // 在设置value时检测和解析token 不符合标准直接抛出异常 前置检验 
    public static Boolean isValidToken() {
        // 解析token 是否得到一个完整对象 
        // HeaderTokenHolder.user = JWTUtils.getUser(HeaderTokenHolder.authenticationToken.get());
        return HeaderTokenHolder.user != null;
    }
    
    public static UserAndPermissionDTO currentUser() {
        if(HeaderTokenHolder.user == null) {
            throw new InaccessibleObjectException("当前threadlocal 对象不存在");
        }
        return HeaderTokenHolder.user;
    }
    
    public static Long currentUserId() {
        if(user != null) {
            return user.getId();
        }
        
        return -1L;
    }
    public static RoleType currentUserRole() {
        // 本类中静态对象可以直接变量名使用 
        if(user != null) {
            return user.getRoleType();
        }
        // 权限系统中 匿名role 几乎不授权 
        return RoleType.ANONYMITY;
    }
    
    
}









