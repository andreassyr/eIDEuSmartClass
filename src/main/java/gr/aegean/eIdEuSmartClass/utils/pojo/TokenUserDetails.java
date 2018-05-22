/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.pojo;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author nikos
 */
public class TokenUserDetails extends User {

    private String token;
    private String profileName;
    private String eid;
    private String personIdentifier;

    public TokenUserDetails(String username, String profileName, String password, 
            String token,  String eid, String personIdentifier,
            boolean enabled, Collection< ? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.profileName = profileName;
        this.token = token;
        this.eid = eid;
        this.personIdentifier = personIdentifier;
    }

    public String getToken() {
        return token;
    }

    public String getProfileName() {
        return profileName;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getPersonIdentifier() {
        return personIdentifier;
    }

    public void setPersonIdentifier(String personIdentifier) {
        this.personIdentifier = personIdentifier;
    }



}
