package ledgerserver.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.joran.conditional.IfAction;
import configuration.ConstantConfig;
import model.user.UserAndPermissionDTO;
import utils.JWTUtils;

@Component // 自动扫描 
@WebFilter(filterName = "tokenVerification", urlPatterns = "/**") 
public class JwtTokenVerificationFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenVerificationFilter.class);
    
    @Override 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // check header token isvalid 
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        log.info("进入过滤器逻辑 JwtTokenVerificationFilter");
        
        String curPath = httpRequest.getRequestURI()
                .substring(httpRequest.getContextPath().length())
                .replaceAll("[/]+$", "");
        
        log.info("current request uri ==> {}, curPath ===> {}", httpRequest.getRequestURI(), curPath);
        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
        Filter.super.init(filterConfig);
    }

}
