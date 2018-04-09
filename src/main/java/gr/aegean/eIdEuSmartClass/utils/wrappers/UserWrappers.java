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
import gr.aegean.eIdEuSmartClass.utils.enums.GenderEnum;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.pojo.TokenUserDetails;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
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

    public static User wrapFormUserToDBUser(FormUser user, RoleService roleServ, GenderService genServ) throws NullPointerException {
        if (user != null) {
            Optional<Role> unregisteredRole = roleServ.getRoleByName(RolesEnum.UNIDENTIFIED.role());
            Optional<Gender> gender = genServ.getGenderByName(user.getGender());
            if(!gender.isPresent()){
                gender = genServ.getGenderByName(GenderEnum.UNSPECIFIED.gender());
            }
            if (unregisteredRole.isPresent() && gender.isPresent()) {
                return new User(unregisteredRole.get(), user.getEid(), user.getCurrentGivenName(), user.getCurrentFamilyName(),
                        user.getEmail(), user.getMobile(), user.getAffiliation(), user.getCountry(), gender.get(),
                        DateWrappers.parseEidasDate(user.getDateOfBirth()), DateWrappers.getNowTimeStamp());
            }else{
                throw new NullPointerException("Role or Gender not found");
            }
        }
        return null;
    }

}
