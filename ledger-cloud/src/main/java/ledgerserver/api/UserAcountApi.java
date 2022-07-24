package ledgerserver.api;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ledgerserver.service.UserAcountService;
import model.UserIdentifierTypeEnum;
import model.user.UserAndPermissionDTO;
import utils.JWTUtils;


@RestController 
@RequestMapping(value = "api/v1/user", name = "user verification and information management") 
public class UserAcountApi {

    private static final Logger log = LoggerFactory.getLogger(UserAcountApi.class);
    
    @Resource 
    private UserAcountService userAcountService;
    
    // 注册用户账号 id = email / phone, 后期均可以修改 
    @PostMapping(value = "regist")
    public ResponseEntity<String> regist(@RequestParam String identifier, @RequestParam String password){
        // identifier 可以是邮箱 电话号码 可以作为id的 内容 
        // 允许用户修改电话或者邮箱 以及匿名名称 --> userName 
        // 注册 1 是否已经存在 2 参数校验,不包含特殊字符 3 密码加密 4 可以增加激活邮件 
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.of(identifier);
        if(userIdentifierType.getMarkName().equals(UserIdentifierTypeEnum.NULL.getMarkName())) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("identifier invalid");
        }
        
        return ResponseEntity.ok("regist successful");
    }
    
    // 发送给用户 激活链接 点击激活动作 
    @PostMapping(value = "active")
    public ResponseEntity<String> active(@RequestParam(name = "email") String emailAddress, 
            @RequestParam(name = "code") String code){
        
        return ResponseEntity.ok("VerificationApi active");
    }
    
    @GetMapping(value = "login")
    public ResponseEntity<String> loginGet(@RequestParam(value = "id") String identifier, 
            @RequestParam(value = "pwd") String password){
        log.info("login request params : {}, {}", identifier, password);
        
        return ResponseEntity.unprocessableEntity().body("need METHOD = POST");
    }
    
    @PostMapping(value = "login")
    public ResponseEntity<String> loginPost(@RequestParam(value = "id") String identifier, 
            @RequestParam(value = "pwd") String password) {
        log.info("login request params : {}, {}", identifier, password);
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.of(identifier);
        if(userIdentifierType.getMarkName().equals(UserIdentifierTypeEnum.NULL.getMarkName())) {
            return ResponseEntity.badRequest().body("identifier invalid");
        }
        UserAndPermissionDTO user = userAcountService.getUserAndPermissions(identifier, password);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid user, User = NULL ! \nPlease user Correct [Identifier] Or [PassWord] ...");
        }
        String token = JWTUtils.getToken(user);
        return ResponseEntity.ok("login successful\n" + user.toString() + "\nToken : \n" + token);
    }
    
    @PostMapping(value = "logout")
    public ResponseEntity<String> logout(@RequestParam(value = "id") Long userId){
        log.info("user id = {} Logout !", userId);
        return ResponseEntity.ok("logout successful");
    }
    
}







