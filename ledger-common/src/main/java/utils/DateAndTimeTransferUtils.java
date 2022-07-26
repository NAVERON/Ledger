package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateAndTimeTransferUtils {

    private static final Logger log = LoggerFactory.getLogger(DateAndTimeTransferUtils.class);
    // LocalDate --> Date 
    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    // Date --> LocalDate 
    public static LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    // date 计算一小时后的时间 Date 
    public static Date expirationDate(Date now) {
        Long expireInMillis = TimeUnit.HOURS.toMillis(1L);
        return new Date(expireInMillis + now.getTime());
    }
    // LocalDateTime --> 1 Hour Later 
    public static LocalDateTime expirationLocalDateTime(LocalDateTime now) {
        return now.plusHours(1);  // 一小时之后 
    }
    
    public static LocalDateTime parseStringToLocalDateTime(String timeString, String formatString) {
        formatString = formatString == null ? "yyyy-MM-dd+HH:mm:ss" : formatString;
        DateTimeFormatter formater = DateTimeFormatter.ofPattern(formatString);
        return LocalDateTime.parse(timeString, formater);
    }
}





