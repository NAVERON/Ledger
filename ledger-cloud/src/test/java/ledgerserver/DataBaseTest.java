package ledgerserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest 
@DataJpaTest 
public class DataBaseTest {

    private static final Logger log = LoggerFactory.getLogger(DataBaseTest.class);
    
    @Test 
    public void simpleTest() {
        log.info("DataBaseTest simpleTest");
        
    }
    
}






