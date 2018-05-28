/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import java.util.List;

/**
 *
 * @author nikos
 */
public interface MailService {

    public String prepareAndSendAccountCreated(String recipient, String userName);
    public String prepareAndSendAccountActivated(String recipient, String name);
    public String prepareAndSendAccountRejected(String recipient, String name);

    
    public String prepareAndSendTeamMessage(String recipient, String name, String teamName, String principalName, String password);
    public String prepareAndSendTeamMessageExisting(String recipient, String name, String teamName, String principal);

    
    public String prepareAndSendSkypeLink(String recipient, String name, String confRoom, String url, String principalName, String password);
    public String prepareAndSendSkypeLinkExisting(String recipient, String name, String confRoom, String url, String principal);

    public String prepeareAndSendPhysicalMail(String recipient, String name, String principalName,String password);
    public String prepeareAndSendPhysicalMailExisting(String recipient, String name, String principalName);
    
    
    public String sendMailToAdmin(String name);
}
