package ledgerserver.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 身份验证等验证信息接口 
 * @author eron 
 * 
 */
@RestController 
@RequestMapping(value = "") 
public class HomeApi {
    
    private static final Logger log = LoggerFactory.getLogger(HomeApi.class);
    
    // 获取api相关的基本信息 可以做成文档形式的api 接口
    @GetMapping(value = "info")
    public ResponseEntity<String> info(){
        
        return ResponseEntity.ok("api infomations : /api/v1/verification");
    }
    
    // 主页 相关
    
    @GetMapping(value = "index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("INDEX : login api = login?identifier=xx&password=xx");
    }
    
    @GetMapping(value = "home")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("HOME : login api = login?identifier=xx&password=xx");
    }
    
    @GetMapping(value = "error")
    public ResponseEntity<String> error(){
        return ResponseEntity.badRequest().body("ERROR !!!");
    }
}









