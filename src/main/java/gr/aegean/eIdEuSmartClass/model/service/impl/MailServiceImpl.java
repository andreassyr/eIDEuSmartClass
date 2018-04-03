/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.service.MailService;
import java.util.Properties;
import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class MailServiceImpl implements MailService {

    private JavaMailSenderImpl mailSender;
    private final static String MAIL_HOST = "smtp.aegean.gr";
    private final static String MAIL_FRIENDLY_NAME = "UAegean Online Communities";
    private final String FROM = "smartclass@aegean.gr";
    private static Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Inject
    public MailServiceImpl(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
        this.mailSender.setHost(MAIL_HOST);
        this.mailSender.setPort(587);
        this.mailSender.setUsername("onlinecommunities@aegean.gr");
        this.mailSender.setPassword("ooo111!!!");
//        this.mailSender.setProtocol("smtp");
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        this.mailSender.setJavaMailProperties(props);
    }

    @Override
    public String prepareAndSend(String recipient, String subject, String userName) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(recipient);
//            helper.setBcc(bcc);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject(subject);

            String content = mailContentBuilder.build(userName);

            helper.setText(content, true);

            mailSender.send(message);

            return "OK";
        } catch (Exception e) {
            log.error("Error sending mail", e.getMessage());
            log.error(e.getMessage());
            return "ERROR";
        }
    }

}
