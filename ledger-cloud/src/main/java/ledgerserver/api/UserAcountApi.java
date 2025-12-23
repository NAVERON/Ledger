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
import model.user.UserAcount;
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
    public ResponseEntity<String> regist(@RequestParam(value = "id", required = true) String identifier, 
            @RequestParam(value = "pwd", required = true) String password, 
            @RequestParam(value = "role", required = false) String role) {
        // identifier 可以是邮箱 电话号码 可以作为id的 内容 
        // 允许用户修改电话或者邮箱 以及匿名名称 --> userName 
        // 注册 1 是否已经存在 2 参数校验,不包含特殊字符 3 密码加密 4 可以增加激活邮件 
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.matchType(identifier);
        if(userIdentifierType.getMarkName().equals(UserIdentifierTypeEnum.NULL.getMarkName())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("identifier invalid");
        }
        
        UserAcount user = this.userAcountService.registUser(userIdentifierType.getValue(), password, role == null ? "" : role);
        log.info("注册用户, 生成随机码 发送邮箱");
        return ResponseEntity.ok(user.toString());
    }
    
    // 发送给用户 激活链接 点击激活动作 
    @PostMapping(value = "active_email")
    public ResponseEntity<String> activeByEmail(@RequestParam(name = "email", required = true) String emailAddress, 
            @RequestParam(name = "code", required = true) String code) {
        UserIdentifierTypeEnum identifierType = UserIdentifierTypeEnum.matchType(emailAddress);
        if(!identifierType.isEmialType()) {
            // 如果不是邮箱地址 直接驳回 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("验证邮箱格式错误");
        }
        UserAcount activedUser = this.userAcountService.activeUser(identifierType, code);
        
        return ResponseEntity.ok("VerificationApi active\n" + activedUser.toString());
    }
    
    @PostMapping(value = "active_phone")
    public ResponseEntity<String> activeByPhone(@RequestParam(name = "phone") String phoneNumber, 
            @RequestParam(name = "code") String code){
        UserIdentifierTypeEnum identifierType = UserIdentifierTypeEnum.matchType(phoneNumber);
        if(!identifierType.isPhoneType()) {
            // 如果不是邮箱地址 直接驳回 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("验证电话格式错误");
        }
        
        UserAcount activedUser = this.userAcountService.activeUser(identifierType, code);
        return ResponseEntity.ok("VerificationApi active\n" + activedUser.toString());
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
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.matchType(identifier);
        if(userIdentifierType.getMarkName().equals(UserIdentifierTypeEnum.NULL.getMarkName())) {
            return ResponseEntity.badRequest().body("identifier invalid");
        }
        UserAndPermissionDTO user = userAcountService.getUserAndPermissions(identifier, password);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("invalid user, User = NULL ! \nPlease user Correct [Identifier] Or [PassWord] ...");
        }
        String token = JWTUtils.getToken(user);
        
        return ResponseEntity.ok(token);
    }
    
    @PostMapping(value = "logout")
    public ResponseEntity<String> logout(@RequestParam(value = "id") Long userId){
        log.info("user id = {} Logout !", userId);
        // 单纯使用token 无法下线 后期补充 redis 强制下线功能 
        log.warn("waiting for TOKEN expiration");
        
        return ResponseEntity.ok("logout successful");
    }
    
}







