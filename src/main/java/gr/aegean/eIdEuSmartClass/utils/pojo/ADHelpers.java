/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.pojo;

import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ActiveDirectoryService;
import gr.aegean.eIdEuSmartClass.model.service.MailService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.enums.EmailTypes;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nikos
 */
public class ADHelpers {

    private final static Logger log = LoggerFactory.getLogger(ADHelpers.class);

    public static String createUserAndSendEmail(Optional<User> user, MailService mailServ, UserService userServ, ActiveDirectoryService adServ,
            String serviceName, String serviceUrl, EmailTypes emailType) {

        String mailName = StringUtils.isEmpty(user.get().getEngName()) ? user.get().getCurrentGivenName() : user.get().getEngName();
        String mailSurname = StringUtils.isEmpty(user.get().getEngSurname()) ? user.get().getCurrentFamilyName() : user.get().getEngSurname();
        if (StringUtils.isEmpty(user.get().getPrincipal())) {
            String creationResponse = adServ.createADCredentialsUpdateUserGetPass(user, userServ);
            if (creationResponse.equals("EXISTS")) {
                sendMailUsingType(emailType, mailServ,
                        user.get().getEmail(), mailName + " " + mailSurname, serviceName, serviceUrl, user.get().getPrincipal(), null, true);
            } else {
                if (!creationResponse.equals("NOK")) {
                    sendMailUsingType(emailType, mailServ,
                            user.get().getEmail(), mailName + " " + mailSurname, serviceName, serviceUrl, user.get().getPrincipal(), creationResponse, false);
                } else {
                    log.info("Error adding user to active directory");
                    return "error";
                }
            }
        } else {
            sendMailUsingType(emailType, mailServ,
                    user.get().getEmail(), mailName + " " + mailSurname, serviceName, serviceUrl, user.get().getPrincipal(), null, true);
        }
        return "OK";

    }

    private static void sendMailUsingType(EmailTypes mailType, MailService mailServ, String recipient,
            String userNameSurname, String serviceName, String serviceUrl, String principal, String password, boolean existing) {

        if (existing) {
            switch (mailType) {
                case AccountCreation:
                    mailServ.prepareAndSendAccountCreated(recipient, userNameSurname);
                    break;
                case AccountActivation:
                    mailServ.prepareAndSendAccountActivated(recipient, userNameSurname);
                    break;
                case AccountRejection:
                    mailServ.prepareAndSendAccountRejected(recipient, userNameSurname);
                    break;
                case Team:
                    mailServ.prepareAndSendTeamMessageExisting(recipient, userNameSurname, serviceName, principal);
                    break;
                case Skype:
                    mailServ.prepareAndSendSkypeLinkExisting(recipient, userNameSurname, serviceName, serviceUrl, principal);
                    break;
                case Physical:
                    mailServ.prepeareAndSendPhysicalMailExisting(recipient, userNameSurname, principal);
                default:
                    break;
            }
        } else {
            switch (mailType) {
                case AccountCreation:
                    mailServ.prepareAndSendAccountCreated(recipient, userNameSurname);
                    break;
                case AccountActivation:
                    mailServ.prepareAndSendAccountActivated(recipient, userNameSurname);
                    break;
                case AccountRejection:
                    mailServ.prepareAndSendAccountRejected(recipient, userNameSurname);
                    break;
                case Team:
                    mailServ.prepareAndSendTeamMessage(recipient, userNameSurname, serviceName, principal, password);
                    break;
                case Skype:
                    mailServ.prepareAndSendSkypeLink(recipient, userNameSurname, serviceName, serviceUrl, principal, password);
                case Physical:
                    mailServ.prepeareAndSendPhysicalMail(recipient, userNameSurname, principal, password);
                default:
                    break;
            }
        }

    }

}
