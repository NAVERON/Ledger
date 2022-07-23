package ledgerserver.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.UserIdentifierTypeEnum;


@RestController 
@RequestMapping(value = "api/v1/user") 
public class UserAcountApi {

    private static final Logger log = LoggerFactory.getLogger(UserAcountApi.class);
    
    // 注册用户账号 id = email / phone, 后期均可以修改 
    @PostMapping(value = "regist")
    public ResponseEntity<String> regist(@RequestParam String identifier, @RequestParam String password){
        // identifier 可以是邮箱 电话号码 可以作为id的 内容 
        // 允许用户修改电话或者邮箱 以及匿名名称 --> userName 
        // 注册 1 是否已经存在 2 参数校验,不包含特殊字符 3 密码加密 4 可以增加激活邮件 
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.of(identifier);
        
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
            return ResponseEntity.badRequest().body("identifier inviled");
        }
        
        return ResponseEntity.ok("login successful");
    }
    
    @PostMapping
    public ResponseEntity<String> logout(){
        
        return ResponseEntity.ok("logout successful");
    }
    
}







