package configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public static final String REDIS_REGIST_CACHE_KEY = "REGIST_USER:";
    
    // 免权限url 不需要验证 token 
    public static final List<String> EXCLUDE_AUTH_URLs = Collections.unmodifiableList(Arrays.asList(
                "/", 
                "/docs/**", 
                "/home", 
                "/index", 
                "/404", 
                "/error", 
                "/**/info", 
                "/**/doc", 
                
                "/api/v1/user/regist", 
                "/api/v1/user/active_email", 
                "/api/v1/user/active_phone", 
                "/api/v1/user/login", 
                "/api/v1/user/logout", 
                
                "/**/*.html", 
                "/**/*.js", 
                "/**/*.css", 
                "/**/*.jpg", 
                "/**/*.png" 
            ));
    
}








