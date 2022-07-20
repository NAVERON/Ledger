package ledgerserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;



@Configuration 
public class RedisConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RedisConfiguration.class);
    
    @Bean 
    public LettuceConnectionFactory lettuceConnectionFactory() {
        
        LettuceConnectionFactory lettuceFactory = new LettuceConnectionFactory("server", 6379);
        return lettuceFactory;
    }
    
    @Bean 
    public RedisTemplate<String, String> redisStringTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisStringTemplpate = new RedisTemplate<>();
        redisStringTemplpate.setConnectionFactory(connectionFactory);
        redisStringTemplpate.setKeySerializer(new StringRedisSerializer());
        redisStringTemplpate.setValueSerializer(new StringRedisSerializer());
        
        return redisStringTemplpate;
    }
    
    
}




