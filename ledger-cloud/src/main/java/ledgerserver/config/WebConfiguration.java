package ledgerserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import configuration.ConstantConfig;

@Configuration 
public class WebConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // authentication interception register configuration 
        WebMvcConfigurer.super.addInterceptors(registry);
        
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(ConstantConfig.EXCLUDE_AUTH_URLs);
        
    }
    
    
    // @Bean  // 使用WebFilter 注解 
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new JwtTokenVerificationFilter());
        filterRegistrationBean.addUrlPatterns("/**");
        
        return filterRegistrationBean;
    }
}




