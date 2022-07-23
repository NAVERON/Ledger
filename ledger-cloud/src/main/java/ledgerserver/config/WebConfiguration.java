package ledgerserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class WebConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        WebMvcConfigurer.super.addInterceptors(registry);
        
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
    }
    
}




