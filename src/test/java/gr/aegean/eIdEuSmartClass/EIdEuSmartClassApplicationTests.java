package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.User;
import gr.aegean.eIdEuSmartClass.model.UserRepository;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EIdEuSmartClassApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createNewUser() {

        LocalDate birthday = LocalDate.now();
        User user = new User("eidas-id", "name", "surname", birthday);

        System.out.println(user.toString());
        userRepository.save(user);

        User user2 = userRepository.findById(user.getId()).get();
        System.out.println(user2.toString());
        assertEquals("name", user2.getName());
    }

    @Test
    public void contextLoads() {
    }

}
