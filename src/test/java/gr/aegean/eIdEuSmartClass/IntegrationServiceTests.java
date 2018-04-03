/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.pojo.BaseResponse;
import javax.transaction.Transactional;
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
    
    @Autowired
    private ClassRoomRepository classRoomRepo;

    @Autowired
    private ClassRoomService roomServ;

    @Test
    public void testSaveNew() {
        BaseResponse resp = userServ.saveUser("eidasTestServ", "n1", "n2", "", "1983-05-10", "test@test.gr", "123456", "ntua", "GR");
        assertEquals(resp.getStatus(), BaseResponse.SUCCESS);
    }

    @Test
    public void testUpdateUserLogin() {
        userServ.saveUser("eidasTestServ2", "n1", "n2", "", "1983-10-10", "test@test.gr", "123456", "ntua", "GR");
        BaseResponse resp = userServ.updateLogin("eidasTestServ2");
        assertEquals(resp.getStatus(), BaseResponse.SUCCESS);
    }

    @Test
    public void testUpdateUserLoginUserNotFound() {
        userServ.saveUser("eidasTestServ3", "n1", "n2", "", "1983-12-20", "test@test.gr", "123456", "ntua", "GR");
        BaseResponse resp = userServ.updateLogin("eidasTestServErr");
        assertEquals(resp.getStatus(), BaseResponse.FAILED);
    }

    @Test
    @Transactional
    public void updateClassRoomState() {
//        roomServ.setRoomStatusByStateName("inactive", "testName");
//        assertEquals(classRepo.findByName("testName").getState().getName(), "Inactive");

    }
}
