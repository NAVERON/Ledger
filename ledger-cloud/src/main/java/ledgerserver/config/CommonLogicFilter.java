package ledgerserver.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component 
@WebFilter(filterName = "common", urlPatterns = "/**") 
public class CommonLogicFilter implements Filter{

    private static final Logger log = LoggerFactory.getLogger(CommonLogicFilter.class);
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("CommonLogicFilter doFilter ---->");
        log.info("params ======= request --> {}", request.getAttributeNames().toString());
        
        chain.doFilter(request, response);
        
    }

}
