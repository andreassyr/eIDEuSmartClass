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
     
    public String prepareAndSend(String recipient, String message, String userName);
   
}
