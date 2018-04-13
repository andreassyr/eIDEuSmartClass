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
     
    public String prepareAndSendAccountCreated(String recipient, String subject, String userName, String adPrincipal, String adPass);
    
    public String prepareAndSendTeamCredentials(String recipient, String teamUserName, String teamPassword, String name);
    
    public String prepareAndSendSkypeLink(String recipient, String name, String url);
   
}
