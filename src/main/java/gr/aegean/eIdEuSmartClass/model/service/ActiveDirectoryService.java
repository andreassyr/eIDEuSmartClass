/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.utils.pojo.ADResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 *
 * @author nikos
 */
public interface ActiveDirectoryService {

    public ADResponse createUser(String displayName, String mailNickname, String givenName, String surname, String userPrincipalName, String password) throws MalformedURLException, IOException;

    public ADResponse createGroup(String displayName, String mailNickname) throws MalformedURLException, IOException;

    public boolean createTeam(String groupId) throws MalformedURLException, IOException;
    
    public boolean sendInvite(String userEmai, String redirectURL, String invitedUserDisplayName) throws MalformedURLException, IOException;
    
    public boolean add2Group(String userId, String groupName, boolean isOwner) throws MalformedURLException, IOException;
    
    public boolean updateUserAttribute(String userId, String attributeName, String attributeValue) throws MalformedURLException, IOException;
    
}
