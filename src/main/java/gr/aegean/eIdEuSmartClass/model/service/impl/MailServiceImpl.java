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

    private final static String MAIL_FRIENDLY_NAME = "UAegean Smart Class";
    private final String FROM = "smartclass@aegean.gr";
    private static Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String prepareAndSendAccountCreated(String recipient, String subject, String userName) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
//            helper.setBcc(bcc);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Smart Class Account initiation");

            String content = MailContentBuilder.buildWelcome(userName);

            helper.setText(content, true);

            mailSender.send(message);

            return "OK";
        } catch (Exception e) {
            log.info("Error sending mail", e);
            return "ERROR";
        }
    }

    @Override
    public String prepareAndSendTeamMessage(String recipient, String name, String teamName, String principalName, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("You have been added to the MS TEAMS from UAegean SMART CLASS");
            String content = MailContentBuilder.buildTeamRegistration(name, teamName, principalName, password);

            helper.setText(content, true);

            mailSender.send(message);

            return "OK";
        } catch (Exception e) {
            log.info("Error sending mail", e);
            return "ERROR";
        }
    }

    @Override
    public String prepareAndSendSkypeLink(String recipient, String name, String url, String principalName, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Your Skype for Business link");
            String content = MailContentBuilder.buildSkypeForBusinessContent(name, url, principalName, password);
            helper.setText(content, true);

            mailSender.send(message);

            return "OK";
        } catch (Exception e) {
            log.info("Error sending mail", e);
            return "ERROR";
        }
    }

    @Override
    public String prepareAndSendAccountActivated(String recipient, String name) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Smart Class Account activation");
            String content = MailContentBuilder.buildAccountActivated(name);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.info("Error sending mail", e);
            return "ERROR";
        }
    }

    @Override
    public String prepareAndSendTeamMessageExisting(String recipient, String name, String teamName, String principal) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("You have been added to the MS TEAMS from UAegean SMART CLASS");
            String content = MailContentBuilder.buildTeamRegistrationExisting(name, teamName, principal);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.info("Error sending email", e);
            return "ERROR";
        }
    }

    @Override
    public String prepareAndSendSkypeLinkExisting(String recipient, String name, String url, String principal) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Your Skype for Business link");

            String content = MailContentBuilder.buildSkypeForBusinessContenExisting(name, url, principal);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.info("Error sending email", e);
            return "ERROR";
        }
    }

}
