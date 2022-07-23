package ledgerserver.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import configuration.ConstantConfig;

/**
 * token 认证过程 拦截器逻辑 
 * @author wangy
 * 
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    /**
     * 
        String scheme = req.getScheme();             // http
        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 80
        String contextPath = req.getContextPath();   // /mywebapp
        String servletPath = req.getServletPath();   // /servlet/MyServlet
        String pathInfo = req.getPathInfo();         // /a/b;c=123
        String queryString = req.getQueryString();          // d=789
     * 
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // controller before 
        // check token 
        log.info("request basic information ----->");
        log.warn("request uri ---> {}, \nrequest URL --> {}, \nrequest query String --> {}", 
                request.getRequestURI(), 
                request.getRequestURL(), 
                request.getQueryString() );
        // 对于首页和登陆页面 不需要拦截 
        
        final String authorizationHeaderValue = request.getHeader(ConstantConfig.AUTHORIZATION_HEADER);
        if(authorizationHeaderValue != null && authorizationHeaderValue.startsWith(ConstantConfig.HEADER_PREFIX)) {
            String token = authorizationHeaderValue.substring(ConstantConfig.HEADER_PREFIX.length());
            log.warn("get Header Token ---> {}", token);
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        
        log.error("preHandle check Header has Token, no Token, please LoginFirst and get Token");
        return HandlerInterceptor.super.preHandle(request, response, handler);
        // return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // controller and before render view , so can  modify modelAndView 
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // response handle 
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
    
    
}


