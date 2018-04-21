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
     
    public String prepareAndSendAccountCreated(String recipient, String subject, String userName);
    
    public String prepareAndSendTeamMessage(String recipient, String name, String teamName, String principalName,String password);
    public String prepareAndSendSkypeLink(String recipient, String name, String url,String principalName,String password);
    
    public String prepareAndSendTeamMessageExisting(String recipient, String name, String teamName,String principal);
    public String prepareAndSendSkypeLinkExisting(String recipient, String name, String url,String principal);
    
    
    public String prepareAndSendAccountActivated(String recipient, String name);
   
   
}
