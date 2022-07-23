package ledgerserver.utils;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component 
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
    
    @Resource
    private RedisTemplate<String, String> stringRedisTemplate; 
    
    
}



