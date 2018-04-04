/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.wrappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.GenderService;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.pojo.TokenUserDetails;
import java.io.IOException;
import java.time.LocalDate;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 *
 * @author nikos
 */
public class UserWrappers {

    public static TokenUserDetails wrapEidasToTokenUser(FormUser usr, String token, PreAuthenticatedAuthenticationToken authentication) throws IOException {
        return new TokenUserDetails(usr.getEid(), usr.getProfileName(), (String) authentication.getCredentials(),
                token, usr.getEid(), usr.getProfileName(), true, authentication.getAuthorities());

    }

    public static FormUser wrapDecodedJwtEidasUser(String jwt) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jwt, FormUser.class);
    }

    public static User wrapFormUserToDBUser(FormUser user, RoleService roleServ, GenderService genServ) {
        if (user != null) {
            Role unregisteredRole = roleServ.getRoleByName("unregistered");
            Gender gender = genServ.getGenderByName(user.getGender());
            return new User(unregisteredRole, user.getEid(), user.getCurrentGivenName(), user.getCurrentFamilyName(),
                    user.getEmail(), user.getMobile(), user.getAffiliation(), user.getCountry(), gender, 
                    DateWrappers.parseEidasDate(user.getDateOfBirth()), DateWrappers.getNowTimeStamp());
        }
        return null;
    }

}
