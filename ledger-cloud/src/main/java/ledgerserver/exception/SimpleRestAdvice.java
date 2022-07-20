package ledgerserver.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice 
public class SimpleRestAdvice {

    private static final Logger log = LoggerFactory.getLogger(SimpleRestAdvice.class);
    
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public void test() {
        log.error("ERROR! --> test");
    }
    
}





