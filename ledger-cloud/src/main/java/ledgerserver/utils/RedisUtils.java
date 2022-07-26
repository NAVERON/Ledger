package ledgerserver.utils;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import configuration.ConstantConfig;
import model.user.UserAcount;
import model.user.UserAndPermissionDTO;
import utils.JsonUtils;

@Component 
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
    
    @Resource 
    private RedisTemplate<String, String> stringRedisTemplate; 
    
    // Redis 缓存验证过的登录用户 token, 可以重复使用 和强制下线 
    public String storeUserToken(UserAndPermissionDTO user, String token) {
        String key = this.generateRedisTokenKey(user);
        Boolean status = stringRedisTemplate.opsForValue().setIfAbsent(key, token, 1, TimeUnit.HOURS);
        
        if(!status) {
            log.info("Already exists, Repeat Set Redis Token for User <{}>", user.getId());
        }
        
        return key;
    }
    
    public String deleteUserToken(UserAndPermissionDTO user) {
        String key = this.generateRedisTokenKey(user);
        String token = stringRedisTemplate.opsForValue().getAndDelete(key);
        
        if(token == null) {
            log.warn("Redis Key is not Exists ! key = {}", key);
            log.warn("reset Token to Empty String");
            token = "";
        }
        
        return token;
    }
    
    
    /**
     * 保存 token 生成redis key的公共方法 
     * @param user
     * @return
     */
    public final String generateRedisTokenKey(UserAndPermissionDTO user) {
        return ConstantConfig.REDIS_TOKEN_PREFIX + user.getId() + "#" + user.getRoleType().name();
    }
    
    // 缓存注册用户 待激活状态, 激活后正式入库 
    public String cacheRegistUser(UserAcount user, String code) {
        log.info("注册用户缓存 --> {}, \nCode = {}", user.toString(), code);
        // user --> json 
        String key = this.generateRedisRegistCacheKey(user.getEmailAddress() + user.getPhoneNumber(), code);
        String userJSON = JsonUtils.toJsonString(user);
        Boolean status = stringRedisTemplate.opsForValue().setIfAbsent(key, userJSON);
        log.info("RedisUtils 设置user JSon --> {}", userJSON);
        
        if(!status) {
            log.error("cacheRegistUser Redis 缓存注册用户 json出现错误 ---> {}", userJSON);
            return null;
        }
        
        return userJSON;
    }
    public UserAcount getRegistUserCache(String identifier, String code) {
        String key = this.generateRedisRegistCacheKey(identifier, code);
        String userJSON = stringRedisTemplate.opsForValue().get(key);
        log.info("获取redis缓存注册对象 --> {}", userJSON);
        stringRedisTemplate.delete(key);
        
        return JsonUtils.fromJsonString(userJSON, UserAcount.class);
    }
    public final String generateRedisRegistCacheKey(String keiIdentifier, String code) {
        return ConstantConfig.REDIS_REGIST_CACHE_KEY + keiIdentifier + ":" + code;
    }
}



