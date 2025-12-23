package ledgerclient.service;

import ledgerclient.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * 获取关于账户相关的内容 
 * @author wangy
 *
 */
public class UserAcountService {

    private static final Logger log = LoggerFactory.getLogger(UserAcountService.class);
    private static final UserAcountService userService = new UserAcountService();
    
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10)
    );

    private final String userBaseURL = "http://localhost:8888/api/v1/user";
    public static Long currentUserID = -1L;
    
    public static UserAcountService getInstance() {
        return userService;
    }
    
    // 使用用户名称 密码登录,返回token 
    public String doLogin(String userName, String password){
        String buildRequestURL = this.userBaseURL.concat("/login?id=").concat(userName).concat("&pwd=").concat(password);
        
        try {
            CompletableFuture<HttpResponse<String>> response = HttpClientUtils.asyncHttpPost(
                    buildRequestURL, ""
            );
            return response.get(10, TimeUnit.SECONDS).body();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public void destory() {
        this.executor.shutdown();
    }
    
    
}








