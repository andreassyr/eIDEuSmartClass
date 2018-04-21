/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.wrappers;

import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.wrappers.UserWrappers;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestUserWrappers {

    @Test
    public void testUserWrapperBYLINGUALUser() throws IOException {
        String jwt = "{\"firstName\":\"ΑΝΔΡΕΑΣ, ANDREAS\",\"eid\":\"GR/GR/ERMIS-11076669\",\"familyName\":\"ΠΕΤΡΟΥ, PETROU\",\"personIdentifier\":\"GR/GR/ERMIS-11076669\",\"dateOfBirth\":\"1980-01-01\"}";
        FormUser user = UserWrappers.wrapDecodedJwtEidasUser(jwt);

        assertEquals(user.getEngName(), "ANDREAS");
    }

    @Test
    public void testUserWrapperONLYGreekUser() throws IOException {
        String jwt = "{\"firstName\":\"ΑΝΔΡΕΑΣ\",\"eid\":\"GR/GR/ERMIS-11076669\",\"familyName\":\"ΠΕΤΡΟΥ\",\"personIdentifier\":\"GR/GR/ERMIS-11076669\",\"dateOfBirth\":\"1980-01-01\"}";
        FormUser user = UserWrappers.wrapDecodedJwtEidasUser(jwt);
        assertEquals(user.getEngName(), null);
        assertEquals(user.getCurrentGivenName(), "ΑΝΔΡΕΑΣ");
    }
    
    @Test
    public void testUserWrapperONLYEnglishUser() throws IOException {
        String jwt = "{\"firstName\":\"ANDREAS\",\"eid\":\"GR/GR/ERMIS-11076669\",\"familyName\":\"PETROU\",\"personIdentifier\":\"GR/GR/ERMIS-11076669\",\"dateOfBirth\":\"1980-01-01\"}";
        FormUser user = UserWrappers.wrapDecodedJwtEidasUser(jwt);
        assertEquals(user.getEngName(), "ANDREAS");
    }

}
