package ledgerclient.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 获取关于账户相关的内容 
 * @author wangy
 *
 */
public class UserAcountService {

    private static final Logger log = LoggerFactory.getLogger(UserAcountService.class);
    private static final UserAcountService userService = new UserAcountService();
    
    private ThreadPoolExecutor executor 
        = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    
    public static final UserAcountService getInstance() {
        return userService;
    }
    
    
    // 使用用户名称 密码登录,返回token 
    public String doLogin(String userName, String password){
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                String name = userName;
                String pwd = password;
                return "";
            }
        };
        
        Future<String> future = executor.submit(callable);
        try {
            // return future.get(10, TimeUnit.SECONDS);
            Thread.sleep(1000);
            return "tmp";
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "tmp";
    }
    
    public void destory() {
        this.executor.shutdown();
    }
    
    
}








