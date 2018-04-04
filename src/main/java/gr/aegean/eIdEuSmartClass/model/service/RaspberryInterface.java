/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

/**
 *
 * @author nikos
 */
public interface RaspberryInterface {
    
    public boolean requestCloseRoom(String roomName) throws MalformedURLException, ProtocolException, IOException;
    
    
}
