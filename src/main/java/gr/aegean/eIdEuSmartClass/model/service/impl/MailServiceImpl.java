/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.service.MailService;
import gr.aegean.eIdEuSmartClass.utils.builder.MailContentBuilder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class MailServiceImpl implements MailService {

    private final static String MAIL_HOST = "smtp.aegean.gr";
    private final static String MAIL_FRIENDLY_NAME = "UAegean Online Communities";
    private final String FROM = "smartclass@aegean.gr";
    private static Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String prepareAndSendAccountCreated(String recipient, String subject, String userName, String adPrincipal, String adPass) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(recipient);
//            helper.setBcc(bcc);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject(subject);

            String content = MailContentBuilder.buildWelcome(userName, adPrincipal, adPass);

            helper.setText(content, true);

            mailSender.send(message);

            return "OK";
        } catch (Exception e) {
            log.error("Error sending mail", e.getMessage());
            log.error(e.getMessage());
            return "ERROR";
        }
    }

    @Override
    public String prepareAndSendTeamCredentials(String recipient, String teamUserName, String teamPassword, String name) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Credentials To Access MS TEAMS from UAegean SMART CLASS");
            String content = MailContentBuilder.buildTeamRegistration(teamUserName, teamPassword, name);

            helper.setText(content, true);

            mailSender.send(message);

            return "OK";
        } catch (Exception e) {
            log.error("Error sending mail", e.getMessage());
            log.error(e.getMessage());
            return "ERROR";
        }
    }

    @Override
    public String prepareAndSendSkypeLink(String recipient, String name, String url) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Credentials To Access MS TEAMS from UAegean SMART CLASS");
            String content = MailContentBuilder.buildSkypeForBusinessContent(name, url);
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
