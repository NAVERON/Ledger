package ledgerserver.utils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


@Component 
@EnableAsync 
public class MailClient {

    private static final Logger log = LoggerFactory.getLogger(MailClient.class);
    
    @Resource 
    private JavaMailSender mailSender;
    @Resource 
    private SpringTemplateEngine templateEngine;
    
    @Value(value = "${spring.mail.username}")
    private String fromEmail;
    
    public void sendSimpleEmail(String to, String subject, String content) {
        String from = this.fromEmail;
        this.sendSimpleEmail(from, to, subject, content);
    }
    
    public void sendHtmlEmail(String to, String subject, String template, Map<String, Object> params) {
        String from = this.fromEmail;
        try {
            this.sendEmailWithHtmlTemplate(from, to, subject, template, params);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("sendHtmlEmail sendEmailWithHtmlTemplate ERROR --> {}", e.toString());
        }
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
    public void sendEmailWithHtmlTemplate(String from, String to, String subject, 
            String template, Map<String, Object> params) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper 
            = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());
        
        Context context = new Context();
        context.setVariables(params);
        String mailContent = templateEngine.process(template, context);
        
        mimeMessageHelper.setText(mailContent, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        
        this.mailSender.send(mimeMessage);
    }
    
    
    // 发送激活邮件 
    public void sendActiveAcountEmail(String to, Map<String, Object> params) {
        log.info("邮件激活参数 --> {}", params);
        
        String subject = "Active U Acount, Just Once ~";
        String template = "emailTemplate/EmailVerificationActive.html";
        this.sendHtmlEmail(to, subject, template, params);
    }
    
}










