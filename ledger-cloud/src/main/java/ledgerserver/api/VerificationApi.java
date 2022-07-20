package ledgerserver.api;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.UserIdentifierTypeEnum;


/**
 * 身份验证等验证信息接口 
 * @author eron 
 * 
 */
@RestController(value = "api/v1/verification") 
public class VerificationApi {
    
    private static final Logger log = LoggerFactory.getLogger(VerificationApi.class);
    
    @RequiresRoles(value = { "anon" })
    @GetMapping(value = "info")
    public ResponseEntity<String> info(){
        
        return ResponseEntity.ok("/api/v1/verification");
    }
    
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
    public ResponseEntity<String> active(@PathVariable(name = "email") String emailAddress, @PathVariable(name = "code") String code){
        
        return ResponseEntity.ok("");
    }
    
    @GetMapping(value = "login")
    public ResponseEntity<String> login(){
        
        return ResponseEntity.unprocessableEntity().body("please METHOD Post");
    }
    
    @PostMapping(value = "login")
    public ResponseEntity<String> login(@RequestParam String identifier, @RequestParam String passWord){
        log.info("login request params : {}, {}", identifier, passWord);
        UserIdentifierTypeEnum userIdentifierType = UserIdentifierTypeEnum.of(identifier);
        
        AuthenticationToken token = new UsernamePasswordToken(userIdentifierType.getValue(), passWord);
        Subject currentUser = SecurityUtils.getSubject();
        log.info("current user ==> [{}]", currentUser.getPrincipal());
        
        currentUser.login(null);
        
        return ResponseEntity.ok("login successful");
    }
    
    @PostMapping
    public ResponseEntity<String> logout(){
        Subject currentUser = SecurityUtils.getSubject();
        
        currentUser.logout();
        
        return ResponseEntity.ok("logout successful");
    }
    
    @GetMapping(value = "test_perm")
    @RequiresPermissions(value = {"add", "update"}, logical = Logical.AND)
    public ResponseEntity<String> testPermission(){
        return ResponseEntity.ok("test permissions");
    }
    
    @GetMapping(value = "test_role")
    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    public ResponseEntity<String> testRoles(){
        
        return ResponseEntity.ok("test roles");
    }
}









