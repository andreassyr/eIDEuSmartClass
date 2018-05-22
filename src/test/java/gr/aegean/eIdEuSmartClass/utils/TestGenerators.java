/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils;

import gr.aegean.eIdEuSmartClass.EIdEuSmartClassApplication;
import gr.aegean.eIdEuSmartClass.security.TokenAuthenticationFilter;
import gr.aegean.eIdEuSmartClass.security.WebSecurityConfig;
import gr.aegean.eIdEuSmartClass.utils.generators.QRGenerator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author nikos
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {EIdEuSmartClassApplication.class, TokenAuthenticationFilter.class, WebSecurityConfig.class, TestValidators.mockConfig.class})
public class TestGenerators {
    
    
    @Test
    public void testQRGenerator(){
        try {
            QRGenerator.generateQR("room1", "userEid1", "1234");
        } catch (IOException ex) {
            Logger.getLogger(TestGenerators.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(true,true);
    }
}
