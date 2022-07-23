package ledgerserver.utils;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;


@Component 
@EnableAsync 
public class MailClient {

    private static final Logger log = LoggerFactory.getLogger(MailClient.class);
    
    @Resource 
    private JavaMailSender mailSender;
    
    @Value(value = "${spring.mail.username}")
    private String fromEmail;
    
    public void sendSimpleEmail(String to, String subject, String content) {
        String from = this.fromEmail;
        this.sendSimpleEmail(from, to, subject, content);
    }
    
    public void sendHtmlEmail(String to, String subject, String content) {
        String from = this.fromEmail;
        this.sendEmailWithHtmlTemplate(from, to, null, from);
    }
    
    @Async 
    public void sendSimpleEmail(String from, String to, String subject, String content) {
        log.info("Mail Client : [{}] ==> [{}]<{}> --> {}", from, to, subject, content);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        
        this.mailSender.send(message);
    }
    
    @Async 
    public void sendEmailWithHtmlTemplate(String from, String to, Map<String, String> params, String template) {
        
    }
    
}








