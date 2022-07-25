package ledgerserver.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mchange.rmi.NotAuthorizedException;

import ledgerserver.annotation.RequiredRolePermission;
import model.user.RoleType;
import model.user.UserAndPermissionDTO;



@Aspect 
@Configuration 
public class ApiAuthenticationAspectConfig {
    
    private static final Logger log = LoggerFactory.getLogger(ApiAuthenticationAspectConfig.class);
    private static PathMatcher defaultPathMatcher = new AntPathMatcher();
    
    // 针对 API 权限 
    @Pointcut(value = "within (ledgerserver.api..*)")
    public void authApiPermission() {
        log.info("authApiPermission PointCut define");
    }
    
    /**
     * 实现用户权限验证的过程 
     * @param processer
     * @throws Throwable
     */
    @Around(value = "authApiPermission()") 
    public Object processApiAuthentication(ProceedingJoinPoint processer) throws Throwable {
        
        // 这里获取请求Attribute 使用的是 ThreadLocal 与工具类中的实现类似 
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpRequest = requestAttributes.getRequest();
        HttpServletResponse httpResponse = requestAttributes.getResponse();
        log.info("进入权限验证 AOP, 获取请求相关信息 --> {}, {}", httpRequest.getQueryString(), httpRequest.getRemoteHost());
        
        // 检查权限 annotation 
        RequiredRolePermission classCheck = processer.getTarget().getClass().getAnnotation(RequiredRolePermission.class);
        if(classCheck != null) {
            // 类上有注解 判断是否有执行权限 
            log.info("class 上有权限校验注解 ");
            
            // 经过token比对后 --> 如果有执行权限,再次判断方法上的  如果没有权限 直接禁止执行 
            // class 校验没有执行权限 直接终止 
            
            RequiredRolePermission methodCheck = ((MethodSignature) processer.getSignature()).getMethod().getAnnotation(RequiredRolePermission.class);
            // 生成权限判断结果 Boolean 
            if(methodCheck != null) {
                // 方法的执行权限 再次判断是否有执行权限 
                // 没有权限直接 返回 
                log.info("Method上有权限校验注解");
                // 抛出异常终止执行 
                // 或者直接返回 ResponseEntity 
                Boolean continueProcess = Boolean.FALSE;
                if(continueProcess) {
                    throw new NotAuthorizedException("Not Authorized Exception !");
                }
            }
        }
        
        Boolean matchStatus = defaultPathMatcher.match("/**/home", httpRequest.getRequestURI());  // 临时检查是否可以实现url匹配 
        log.warn("检查匹配结果 --> {}", matchStatus);
        
        StopWatch sw = new StopWatch();
        sw.start();
        Object result = processer.proceed();
        sw.stop();
        log.info("AOP 统计执行时间 --> {}", sw.getTotalTimeMillis());
        
        return result;
    }
    
    /**
     * 临时 : url校验匹配 
     * private static PathMatcher defaultPathMatcher = new AntPathMatcher();  // url 匹配器 解释注解中的url匹配规则 
     * 
     */
    public Boolean isAllowed(RequiredRolePermission requiredRolePermission, String requestURI, UserAndPermissionDTO user) {
        // 判断 url 匹配 
        Boolean urlMatchStatus = defaultPathMatcher.match(requiredRolePermission.urlPattern(), requestURI);
        // role权限是否更大 
        Boolean roleMatchStatus = user.getRoleType().isBiggerThan(requiredRolePermission.role());
        // token用户权限是否全部包含当前权限 
        Boolean permissionMatchStatus = user.getPermissions().containsAll(Arrays.asList(requiredRolePermission.permission()));
        
        return urlMatchStatus && roleMatchStatus && permissionMatchStatus;
    }
    
    
}









