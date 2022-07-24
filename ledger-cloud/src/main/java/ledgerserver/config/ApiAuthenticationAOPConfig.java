package ledgerserver.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect 
@Component 
public class ApiAuthenticationAOPConfig {

    private static final Logger log = LoggerFactory.getLogger(ApiAuthenticationAOPConfig.class);
    
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
        
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpRequest = requestAttributes.getRequest();
        HttpServletResponse httpResponse = requestAttributes.getResponse();
        log.info("进入权限验证 AOP, 获取请求相关信息 --> {}, {}", httpRequest.getQueryString(), httpRequest.getRemoteHost());
        
        StopWatch sw = new StopWatch();
        
        sw.start();
        Object result = processer.proceed();
        sw.stop();
        log.info("AOP 统计执行时间 --> {}", sw.getTotalTimeMillis());
        
        return result;
    }
    
}


