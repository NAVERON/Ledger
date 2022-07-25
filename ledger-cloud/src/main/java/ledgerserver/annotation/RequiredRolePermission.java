package ledgerserver.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import model.user.RoleType;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented 
public @interface RequiredRolePermission {  // 权限注解 
    
    // 三个条件同时满足才能有操作权限 
    String urlPattern() default "/**";
    RoleType role() default RoleType.ANONYMITY;
    String[] permission() default {};
    
}
