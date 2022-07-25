package configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.user.UserAndPermissionDTO;

/**
 * 设置型的常量参数 
 * @author eron
 *
 */
public class ConstantConfig {
    
    public static final String PERMISSION_SPLIT_COMMA = ",";  // permission List --> String delimiter 
    
    public static final String AUTHORIZATION_HEADER = "Authorization";  // header key 
    public static final String AUTH_HEADER_PREFIX = "Bearer ";  // header value prefix 
    
    public static final String JWT_SIGNATURE_KEY = "wangyulongwangyulongwangyulongwangyulongwangyulong";  // 需要超过 512 bits
    public static final String JWT_SUBJECT = "AUTH_USER_PERMISSION#CLIENT";
    public static final String JWT_ISSUER = "LEDGER#CLIENT";
    
    public static final String REDIS_TOKEN_PREFIX = "AUTH_TOKEN:";
    
    // 免权限url 不需要验证 token 
    public static final List<String> EXCLUDE_AUTH_URLs = Collections.unmodifiableList(Arrays.asList(
                "/home", 
                "/index", 
                "/404", 
                "/error", 
                "/**/info", 
                
                "/api/v1/user/regist", 
                "/api/v1/user/active", 
                "/api/v1/user/login", 
                "/api/v1/user/logout", 
                
                "/**/*.html", 
                "/**/*.js", 
                "/**/*.css", 
                "/**/*.jpg", 
                "/**/*.png" 
            ));
    
    
    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    public static LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static Date expirationDate(Date now) {
        Long expireInMillis = TimeUnit.HOURS.toMillis(1L);
        return new Date(expireInMillis + now.getTime());
    }
    
    public static final String generateRedisTokenKey(UserAndPermissionDTO user) {
        return ConstantConfig.REDIS_TOKEN_PREFIX + user.getId() + "#" + user.getRoleType().name();
    }
}








