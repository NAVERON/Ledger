package ledgerserver.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController 
@RequestMapping(value = "api/v1/statistic", name = "statistic api for api usage") 
public class StatisticApi {

    private static final Logger log = LoggerFactory.getLogger(StatisticApi.class);

    @GetMapping(value = "info")
    public ResponseEntity<String> info() {
        
        return ResponseEntity.status(HttpStatus.OK).body("information");
    }
    
    
}










