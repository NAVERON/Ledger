package ledgerserver;

import java.util.Arrays;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;


@SpringBootApplication 
public class LedgerSpringStarter implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(LedgerSpringStarter.class);
    
    @Resource 
    private Environment environment;

    public static void main(String[] args) {
        log.info("Ledger Server Springboot Run ...");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(LedgerSpringStarter.class);
        builder.build().run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("command line running ... {}", Arrays.asList(environment.getActiveProfiles()));
    }
    
    
}






