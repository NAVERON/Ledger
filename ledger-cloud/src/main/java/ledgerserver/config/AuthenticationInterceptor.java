package ledgerserver.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import configuration.ConstantConfig;
import model.user.UserAndPermissionDTO;
import utils.JWTUtils;

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
        log.warn("request uri ---> {}, request URL --> {}, request query String --> {}", 
                request.getRequestURI(), 
                request.getRequestURL(), 
                request.getQueryString() );
        log.error("preHandle check Header has Token, no Token, please LoginFirst and get Token");
        // 对于首页和登陆页面 不需要拦截 在webMvcConfiger 中直接配置 excludePathPattern 
        String curPath = request.getRequestURI()
                                    .substring(request.getContextPath().length())
                                    .replaceAll("[/]+$", "");
        log.info("计算当前 current path == > {}", curPath);
        
        final String authorizationHeader = request.getHeader(ConstantConfig.AUTHORIZATION_HEADER);
        // 检查是否包含有效的token 不判断权限问题 
        if(authorizationHeader != null && authorizationHeader.startsWith(ConstantConfig.AUTH_HEADER_PREFIX)) {
            String token = authorizationHeader.substring(ConstantConfig.AUTH_HEADER_PREFIX.length());
            log.warn("get Header Token ---> {}", token);
            
            UserAndPermissionDTO user = JWTUtils.getUser(token);
            // 是否需要数据库检查是否是有效用户 ? 后期增加多重验证  需要验证jwt是否过期 强制用户刷新jwt重新请求接口, 但是会引入状态问题 后期考虑 
            if(user != null) {
                log.info("解析出来的user对象 --> {}", user.toString());
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        }
        log.info("please login for generate Token for U ! Header 没有 JWT Token, 请登录 api 获取");
        log.warn("跳转 ---> {}", request.getContextPath() + "/home");
        response.sendRedirect("/home");
        
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // controller and before render view , so can  modify modelAndView 
        log.info("AuthenticationInterceptor postHandle --> {}", request.toString());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // response handle 
        log.info("AuthenticationInterceptor afterCompletion --> {}", response.toString());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
    
    
}








