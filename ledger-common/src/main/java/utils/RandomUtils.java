package utils;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RandomUtils {
    
    private static final Logger log = LoggerFactory.getLogger(RandomUtils.class);
    private static final Random random = new Random();
    private static String ALPHANUMERIC_ENUMs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                                + "0123456789"
                                                + "abcdefghijklmnopqrstuvxyz";
    
    // 生成一个指定长度的字符串 length = n 
    public static String generateRandomString(Integer n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = random.nextInt(ALPHANUMERIC_ENUMs.length());
            sb.append(ALPHANUMERIC_ENUMs.charAt(index));
        }
        return sb.toString();
    }
    
    public static Long generateRandomLong(Long start, Long end) {
        return random.nextLong(start, end);
    }
    
    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

}
