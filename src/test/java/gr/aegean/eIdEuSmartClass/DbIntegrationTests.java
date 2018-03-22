package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.dao.RoleRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.dao.UserRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createNewUser() {

        Role r = new Role("test");
        Gender g = new Gender("n/a");

        LocalDate birthday = LocalDate.now();
        User user = new User("eidas-id2", "name2", "surname", birthday, r, g);

        System.out.println(user.toString());
        userRepository.save(user);

        User user2 = userRepository.findById(user.getId()).get();
        System.out.println(user2.toString());
        assertEquals(user.getName(), user2.getName());
    }

//    @Test
    public void contextLoads() {
    }

}
