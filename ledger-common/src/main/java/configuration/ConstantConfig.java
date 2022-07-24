package configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 设置型的常量参数 
 * @author eron
 *
 */
public class ConstantConfig {
    public static final String PERMISSION_SPLIT_COMMA = ",";
    
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String HEADER_PREFIX = "Bearer ";
    
    public static final Integer JWT_EXPIRATION_HOURS = 1;
    public static final String JWT_SIGNATURE_KEY = "wangyulongwangyulongwangyulongwangyulongwangyulong";  // 需要超过 512 bits
    public static final String JWT_SUBJECT = "AUTH_USER_PERMISSION";
    public static final String JWT_ISSUER = "LEDGER";
    
    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    public static LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public static Date expirationDate(Date now) {
        Long expireInMillis = TimeUnit.HOURS.toMillis(ConstantConfig.JWT_EXPIRATION_HOURS);
        return new Date(expireInMillis + now.getTime());
    }
    
    // 免权限url 不需要验证 token 
    public static final List<String> EXCLUDE_AUTH_URLs = Collections.unmodifiableList(Arrays.asList(
                "/**/home", 
                "/**/index", 
                // "/**/info", 
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
    
    
}








