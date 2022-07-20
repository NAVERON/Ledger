package ledgerserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc 
@TestPropertySource(locations = "classpath:application-dev.properties")
public class LoginProcedureTest {

    private static final Logger log = LoggerFactory.getLogger(LoginProcedureTest.class);
    @Autowired 
    private MockMvc mvc;
    
    @Test 
    public void simpleTest() {
        log.info("simpleTest");
        
    }
    
    
}





