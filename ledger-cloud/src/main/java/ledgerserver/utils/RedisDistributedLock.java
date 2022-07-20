package ledgerserver.utils;


import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisException;
import io.lettuce.core.RedisURI;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.StatefulRedisConnection;


/**
 * 包装redis和zookeeper实现的分布式锁 
 * @author eron 
 * 
 */
public class RedisDistributedLock {

    private static final Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);
    
    private RedisURI redisURI = null;
    private RedisClient client = null;
    private String lockPrefix = "Lock_";
    private Long timeout = 1000L;
    
    public RedisDistributedLock() {
        
        this.redisURI = RedisURI.Builder 
                        .redis("localhost") 
                        .withPassword("authentication".toCharArray()) 
                        .build();
        this.client = RedisClient.create(this.redisURI);
        
    }
    public RedisDistributedLock(RedisURI redisURI) {
        this.redisURI = redisURI;
        this.client = RedisClient.create(this.redisURI);
    }
    
    public String tryLock(String resourceName, Long expireTime) {
        String lockKey = this.lockPrefix + resourceName;
        String lockIdentifire = null;
        StatefulRedisConnection<String, String> connection = null;
        
        try {
            connection = this.client.connect();
            
            String uuid = UUID.randomUUID().toString();
            Long acquireEndTime = System.currentTimeMillis() + this.timeout;
            
            while(acquireEndTime < System.currentTimeMillis()) {
                Boolean status = connection.sync().setnx(lockKey, uuid);
                if(status) {
                    // 设置成功 
                    connection.sync().expire(lockKey, expireTime/1000);
                    lockIdentifire = uuid;
                    return lockIdentifire;
                }
                
                if(connection.sync().ttl(lockKey) == -1) {
                    // 当前锁定资源过时 延长过期时间
                    connection.sync().expire(lockKey, expireTime/1000);
                }
                
                Thread.sleep(50);
            }
            
        }catch (RedisException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(connection != null) {
                connection.close();
            }
        }
        
        return lockIdentifire;
    }
    
    // 尝试释放redis锁 
    public Boolean tryRelease(String resourceName, String lockIdentifire) {
        String lockKey = this.lockPrefix + resourceName;
        Boolean releaseFlag = false;
        StatefulRedisConnection<String, String> connection = null;
        
        try {
            connection = this.client.connect();
            
            while(true) {
                // 对lockKey 开启事务 multi + watch 
                connection.sync().watch(lockKey);
                if(lockIdentifire.equals(connection.sync().get(lockKey))) {
                    // 如果当前锁存在 则释放 
                    connection.sync().multi();
                    connection.sync().del(lockKey);
                    TransactionResult result = connection.sync().exec();
                    if(result.isEmpty()) {  // 上层的死循环就是为这里设计的 
                        continue;
                    }
                    
                    releaseFlag = true;
                }
                connection.sync().unwatch();
                break;
            }
            
        }catch (RedisException e) {
            e.printStackTrace();
        }
        
        return releaseFlag;
    }
    
}


