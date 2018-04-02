/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.wrappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.aegean.eIdEuSmartClass.utils.pojo.EidasUser;
import gr.aegean.eIdEuSmartClass.utils.pojo.TokenUserDetails;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 *
 * @author nikos
 */
public class UserWrappers {

    public static TokenUserDetails wrapEidasToTokenUser(EidasUser usr, String token, PreAuthenticatedAuthenticationToken authentication) throws IOException {
        return new TokenUserDetails(usr.getEid(), usr.getProfileName(), (String) authentication.getCredentials(),
                token, usr.getEid(), usr.getProfileName(), true, new ArrayList());

    }

    public static EidasUser wrapDecodedJwtEidasUser(String jwt) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jwt, EidasUser.class);
    }
}
