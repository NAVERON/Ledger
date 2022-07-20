package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.UserIdentifierTypeEnum;


public class RegexMatcher {

    private static final Logger log = LoggerFactory.getLogger(RegexMatcher.class);
    
    private static final String telePhoneRegex = "^[1][3,4,5,7,8][0-9]{9}$";
    private static final String normalPhoneRegex = "0\\d{2,3}-\\d{7,8}";
    private static final String emailRegex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
    
    private static Pattern telePhoneNumberPattern = Pattern.compile(telePhoneRegex);
    private static Pattern emailAddressPattern = Pattern.compile(emailRegex);
    
    public static Boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = telePhoneNumberPattern.matcher(phoneNumber);
        return matcher.matches();
    }
    
    public static Boolean isValidEmailAddress(String emailAddress) {
        Matcher matcher = emailAddressPattern.matcher(emailAddress);
        return matcher.matches();
    }
    
    public static UserIdentifierTypeEnum userIdentifierType(String userIdentifier) {
        UserIdentifierTypeEnum type = UserIdentifierTypeEnum.of(userIdentifier);
        return type;
    }
    
}









