package ledger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;




@SpringBootApplication 
public class LedgerSpringStarter {

    public static void main(String[] args) {
        System.out.println("Ledger Cloud Service ...");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(LedgerSpringStarter.class);
        builder.run(args);
    }
    
    
}






