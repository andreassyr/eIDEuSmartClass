/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 *
 * @author nikos
 */
public interface ActiveDirectoryService {
    
    
    public boolean registerUser(String userEmail)  throws MalformedURLException, IOException;
    
}
