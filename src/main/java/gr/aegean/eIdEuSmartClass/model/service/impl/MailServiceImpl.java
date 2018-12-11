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
    public String prepareAndSendAccountCreated(String recipient, String userName) {
        log.info("Sending email to " +recipient);
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
        log.info("Sending email to " +recipient);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Welcome to online class " + teamName);
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
    public String prepareAndSendSkypeLink(String recipient, String name, String confRoom, String url, String principalName, String password) {
        log.info("Sending email to " +recipient);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Welcome to conference room" + confRoom);
            String content = MailContentBuilder.buildSkypeForBusinessContent(name, confRoom, url, principalName, password);
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
        log.info("Sending email to " +recipient);
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
        log.info("Sending email to " +recipient);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Welcome to online class" + teamName);
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
    public String prepareAndSendSkypeLinkExisting(String recipient, String name, String confRoom, String url, String principal) {
        log.info("Sending email to " +recipient);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Welcome to conference room" + confRoom);

            String content = MailContentBuilder.buildSkypeForBusinessContenExisting(name, confRoom, url, principal);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.info("Error sending email", e);
            return "ERROR";
        }
    }

    @Override
    public String sendMailToAdmin(String name) {
        log.info("Sending email to admin");
        String[] admins = {"triantafyllou.ni@gmail.com","pkavassalis@atlantis-group.gr","adanar@atlantis-group.gr","msofianop@gmail.com"};
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(admins);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("New smartclass account requested!");
            String content = MailContentBuilder.buildNewAccountInfoAdmin(name);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.info("Error sending mail", e);
            return "ERROR";
        }
    }

    @Override
    public String prepareAndSendAccountRejected(String recipient, String name) {
        log.info("Sending email to " +recipient);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject(" Your registration with Smart Class");
            String content = MailContentBuilder.buildAccountRejected(name);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.info("Error sending mail", e);
            return "ERROR";
        }
    }

    @Override
    public String prepeareAndSendPhysicalMail(String recipient, String name, String principalName, String password) {
        log.info("Sending email to " +recipient);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Welcome to Smart Class Physical Facilities (accessed via IoT)");
            String content = MailContentBuilder.buildPhysicalAccount(name, principalName, password);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.info("Error sending mail", e);
            return "ERROR";
        }
    }

    @Override
    public String prepeareAndSendPhysicalMailExisting(String recipient, String name, String principalName) {
        log.info("Sending email to " +recipient);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(recipient);
            helper.setFrom(new InternetAddress(FROM, MAIL_FRIENDLY_NAME));
            helper.setSubject("Welcome to Smart Class Physical Facilities (accessed via IoT)");
            String content = MailContentBuilder.buildPhysicalAccountExisting(name, principalName);
            helper.setText(content, true);
            mailSender.send(message);
            return "OK";
        } catch (Exception e) {
            log.info("Error sending mail", e);
            return "ERROR";
        }
    }

}
