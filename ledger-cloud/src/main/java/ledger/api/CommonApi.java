package ledger.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping(value = "api/v1/common")
public class CommonApi {

    private static final Logger log = LoggerFactory.getLogger(CommonApi.class);

    @GetMapping(value = "test")
    public ResponseEntity<String> test() {
        
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    
}










