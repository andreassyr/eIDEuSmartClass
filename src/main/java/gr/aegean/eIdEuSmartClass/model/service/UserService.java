/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.utils.pojo.RasberyrResponse;

/**
 *
 * @author nikos
 */
public interface UserService {
    
    public RasberyrResponse saveUser(User user);
    
    public RasberyrResponse saveUser(String eIDASid, String name, String surname, String Gender, String DateOfBirth);
    
    public RasberyrResponse updateLogin(String eIDasid);
}
