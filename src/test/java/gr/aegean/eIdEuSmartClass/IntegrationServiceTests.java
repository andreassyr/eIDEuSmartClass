/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.pojo.RasberyrResponse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author nikos
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationServiceTests {

    @Autowired
    private UserService userServ;

    
    @Test
    public void  testSaveNew(){
        RasberyrResponse resp =  userServ.saveUser("eidasTestServ", "n1", "n2", "", "10/10/1983");
        assertEquals(resp.getStatus(),RasberyrResponse.SUCCESS);
    }
    
}
