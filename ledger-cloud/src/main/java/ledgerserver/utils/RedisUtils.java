package ledgerserver.utils;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import configuration.ConstantConfig;
import model.user.UserAndPermissionDTO;

@Component 
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
    
    @Resource 
    private RedisTemplate<String, String> stringRedisTemplate; 
    
    // Redis 缓存验证过的登录用户 token, 可以重复使用 和强制下线 
    public String storeUserToken(UserAndPermissionDTO user, String token) {
        String key = ConstantConfig.generateRedisTokenKey(user);
        Boolean status = stringRedisTemplate.opsForValue().setIfAbsent(key, token, 1, TimeUnit.HOURS);
        
        if(!status) {
            log.info("Already exists, Repeat Set Redis Token for User <{}>", user.getId());
        }
        
        return key;
    }
    
    public String deleteUserToken(UserAndPermissionDTO user) {
        String key = ConstantConfig.generateRedisTokenKey(user);
        String token = stringRedisTemplate.opsForValue().getAndDelete(key);
        
        if(token == null) {
            log.warn("Redis Key is not Exists ! key = {}", key);
            log.warn("reset Token to Empty String");
            token = "";
        }
        
        return token;
    }
    
    
}



