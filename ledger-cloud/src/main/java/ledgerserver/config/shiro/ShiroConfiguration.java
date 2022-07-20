package ledgerserver.config.shiro;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class ShiroConfiguration {
    
    private static final Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);
    
    @Bean(name = "shiroFilterFactoryBean") 
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 设置登录url 
        shiroFilterFactoryBean.setLoginUrl("/api/v1/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/v1/loginerror");
        shiroFilterFactoryBean.setSuccessUrl("/api/v1/user/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        
        return shiroFilterFactoryBean;
    }
    
    @Bean(name = "shiroFilterChainDefinition") 
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/api/v1/login", "anon");
        chainDefinition.addPathDefinition("/api/v1/**", "authc");
        chainDefinition.addPathDefinition("/api/v1/logout", "logout");
        return chainDefinition;
    }
    
    @Bean(name = "securityManager") 
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        
        return securityManager;
    }
    
    // 获取自定义authorizing config 
    //@Bean(name = "myRealm") 
    public AuthorizingRealm myRealm() {
        AuthorizingRealm customeRealm = new CustomeLoginAuthorizing();
        customeRealm.setCredentialsMatcher(credentialsMatcher());
        
        return customeRealm;
    }
    
    //@Bean(name = "credentialsMatcher") 
    public CredentialsMatcher credentialsMatcher() {  // 加密设置 
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        
        return hashedCredentialsMatcher;
    }
    

}










