/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.utils.pojo.ADResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

/**
 *
 * @author nikos
 */
public interface ActiveDirectoryService {

    public ADResponse createUser(String displayName, String mailNickname, String givenName, String surname,
            String userPrincipalName, String password, String eId) throws MalformedURLException, IOException;

    public ADResponse createGroup(String displayName, String mailNickname) throws MalformedURLException, IOException;

    public ADResponse createTeam(String groupId) throws MalformedURLException, IOException;
    
    public ADResponse sendInvite(String userEmail, String redirectURL, String invitedUserDisplayName) throws MalformedURLException, IOException;
    
    public ADResponse add2Group(String userId, String groupName, boolean isOwner) throws MalformedURLException, IOException;
    
    public ADResponse updateUserAttribute(String userId, String attributeName, String attributeValue) throws MalformedURLException, IOException;
    
    public String createADCredentialsUpdateUserGetPass(Optional<User> user, UserService userServ);
    
    
}
