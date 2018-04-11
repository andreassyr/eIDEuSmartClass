/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.pojo.BaseResponse;
import java.util.Optional;
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
    
    @Autowired
    private RoleService roleServ;
    

    @Test
    public void testSaveNew() {
        BaseResponse resp = userServ.saveOrUpdateUser("eidasTestServ", "n1", "n2", "", "1983-05-10", "test@test.gr", "123456", "ntua", "GR",null);
        assertEquals(resp.getStatus(), BaseResponse.SUCCESS);
    }

    @Test
    public void testUpdateUserLogin() {
        userServ.saveOrUpdateUser("eidasTestServ2", "n1", "n2", "", "1983-10-10", "test@test.gr", "123456", "ntua", "GR",null);
        BaseResponse resp = userServ.updateLogin("eidasTestServ2");
        assertEquals(resp.getStatus(), BaseResponse.SUCCESS);
    }

    @Test
    public void testUpdateUserLoginUserNotFound() {
        userServ.saveOrUpdateUser("eidasTestServ3", "n1", "n2", "", "1983-12-20", "test@test.gr", "123456", "ntua", "GR",null);
        BaseResponse resp = userServ.updateLogin("eidasTestServErr");
        assertEquals(resp.getStatus(), BaseResponse.FAILED);
    }

    @Test
    @Transactional
    public void updateClassRoomState() {
//        roomServ.setRoomStatusByStateName("inactive", "testName");
//        assertEquals(classRepo.findByName("testName").getState().getName(), "Inactive");

    }

    @Test
    @Transactional
    public void updateUserRole() {
        userServ.saveOrUpdateUser("updateUser", "n1", "n2", "", "1983-05-10", "test@test.gr", "123456", "ntua", "GR",null);
        roleServ.updateUserRole("updateUser", RolesEnum.ADMIN.role());
        Optional<User> user = userServ.findByEid("updateUser");
        assertEquals(user.get().getRole().getName(),RolesEnum.ADMIN.role());
    }
    
    
    @Test
    @Transactional
    public void updateUserRoleRoleNOTFOUND() {
        userServ.saveOrUpdateUser("updateUser", "n1", "n2", "", "1983-05-10", "test@test.gr", "123456", "ntua", "GR",null);
        roleServ.updateUserRole("updateUser", "foobar");
        Optional<User> user = userServ.findByEid("updateUser");
        assertEquals(user.get().getRole().getName(),RolesEnum.UNREGISTERED.role());
    }
    
}
