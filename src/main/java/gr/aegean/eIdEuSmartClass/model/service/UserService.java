/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.utils.pojo.BaseResponse;
import java.util.Optional;

/**
 *
 * @author nikos
 */
public interface UserService {
    
    public BaseResponse saveUser(User user);
    
    public BaseResponse saveOrUpdateUser(String eIDASid, String name, String surname, String gender, String dateOfBirth, String email,
            String mobile, String affiliation, String country, String adId);
    
    public BaseResponse updateLogin(String eIDasid);
    
    public Optional<User> findByEid(String eID);
}
