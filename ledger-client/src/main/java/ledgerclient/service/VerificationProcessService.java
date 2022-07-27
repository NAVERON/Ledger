package ledgerclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证 注册 身份认证等过程服务  可以被账号服务调用 
 * @author eron
 * 
 */
public class VerificationProcessService {

    private static final Logger log = LoggerFactory.getLogger(VerificationProcessService.class);
    private static final VerificationProcessService verificationService = new VerificationProcessService();
    
    public static final VerificationProcessService getInstance() {
        return verificationService;
    }
    
}



