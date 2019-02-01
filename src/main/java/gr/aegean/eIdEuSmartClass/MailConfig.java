/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author nikos
 */
@Configuration
public class MailConfig {

    private final static String MAIL_HOST = "smtp.aegean.gr";
    private final static String MAIL_FRIENDLY_NAME = "UAegean Online Communities";
    private final String FROM = "smartclass@aegean.gr";

    @Value("${mail.pass}")
    private String pass;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(MAIL_HOST);
        javaMailSender.setPort(587);
        javaMailSender.setUsername("onlinecommunities@aegean.gr");
        javaMailSender.setPassword(pass);
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return props;
    }
}
