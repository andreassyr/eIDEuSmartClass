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
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 *
 * @author nikos
 */
public class UserWrappers {

    
    private static Logger log = LoggerFactory.getLogger(UserWrappers.class);
    
    private static String IS_LATIN = "[\\p{Punct}\\p{Space}\\p{IsLatin}]+$";
    // "[\\p{L}\\p{M}&&[^\\p{Alpha}]]+";

    public static TokenUserDetails wrapEidasToTokenUser(FormUser usr, String token, PreAuthenticatedAuthenticationToken authentication) throws IOException {
        return new TokenUserDetails(usr.getEid(), usr.getProfileName(), (String) authentication.getCredentials(),
                token, usr.getEid(), usr.getProfileName(), true, authentication.getAuthorities());

    }

    public static FormUser wrapDecodedJwtEidasUser(String jwt) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FormUser user = mapper.readValue(jwt, FormUser.class);
        user.setEngName(user.getCurrentGivenName());
        user.setEngSurname(user.getCurrentFamilyName());
        if (!(user.getCurrentFamilyName().matches(IS_LATIN) && user.getCurrentFamilyName().matches(IS_LATIN))) {
            //non latin found in namae or surname
            String engGiveName = user.getCurrentGivenName().split(",").length > 1 ? user.getCurrentGivenName().split(",")[1].trim() : null;
            String engFamilyName = user.getCurrentFamilyName().split(",").length > 1 ? user.getCurrentFamilyName().split(",")[1].trim() : null;

            String natGiveName = user.getCurrentGivenName().split(",").length > 1 ? user.getCurrentGivenName().split(",")[0].trim() : user.getCurrentGivenName();
            String natFamilyName = user.getCurrentFamilyName().split(",").length > 1 ? user.getCurrentFamilyName().split(",")[0].trim() : user.getCurrentFamilyName();
            user.setEngSurname(engFamilyName);
            user.setEngName(engGiveName);
            user.setCurrentGivenName(natGiveName);
            user.setCurrentFamilyName(natFamilyName);
        }
        return user;
    }

    public static User wrapFormUserToDBUser(FormUser user, RoleService roleServ, GenderService genServ) throws NullPointerException {
        if (user != null) {
            Optional<Role> unregisteredRole = roleServ.getRoleByName(RolesEnum.UNIDENTIFIED.role());
            Optional<Gender> gender = genServ.getGenderByName(user.getGender());
            if (!gender.isPresent()) {
                gender = genServ.getGenderByName(GenderEnum.UNSPECIFIED.gender());
            }
            if (unregisteredRole.isPresent() && gender.isPresent()) {
                return new User(unregisteredRole.get(), user.getEid(), user.getCurrentGivenName(), user.getCurrentFamilyName(),
                        user.getEmail(), user.getMobile(), user.getAffiliation(), user.getCountry(), gender.get(),
                        DateWrappers.parseEidasDate(user.getDateOfBirth()), DateWrappers.getNowTimeStamp(), user.getEngName(), user.getEngSurname());
            } else {
                throw new NullPointerException("Role or Gender not found");
            }
        }
        return null;
    }

    public static FormUser wrapDBUsertoFormUser(User user) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        FormUser res = new FormUser();
        res.setAffiliation(user.getAffiliation());
        res.setCountry(user.getCountry());
        res.setCurrentFamilyName(user.getCurrentFamilyName());
        res.setCurrentGivenName(user.getCurrentGivenName());
        try {
            res.setDateOfBirth(df.format(user.getDateOfBirth()));
        } catch (Exception e) {
            log.info("No DATE found",e);
        }
        res.setEid(user.getEid());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender().getName());
        res.setMobile(user.getMobile());
        res.setPersonIdentifier(user.getEid());
        res.setEngName(user.getEngName());
        res.setEngSurname(user.getEngSurname());
        //res.setProfileName(user.get);

        return res;
    }

}
