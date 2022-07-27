package ledgerclient.service;

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
    
    public static final UserAcountService getInstance() {
        return userService;
    }
    
}




