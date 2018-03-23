package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomStateRepository;
import gr.aegean.eIdEuSmartClass.model.dao.GenderRepository;
import gr.aegean.eIdEuSmartClass.model.dao.RoleRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.dao.UserRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoomState;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import java.time.LocalDate;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbIntegrationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private ClassRoomStateRepository classRoomStatusRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void createNewUser() {

        Role storedRole = roleRepository.findByName("unregistered").get();
        Gender storedGender = genderRepository.findByName(Gender.MALE).get();

        LocalDate birthday = LocalDate.now();
        User user = new User("eidas-id2", "name2", "surname", birthday, storedRole, storedGender);

        System.out.println(user.toString());
        userRepository.save(user);

        User user2 = userRepository.findById(user.getId()).get();
        System.out.println(user2.toString());
        assertEquals(user.getName(), user2.getName());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void createClassroom() {
        ClassRoomState classState = classRoomStatusRepository.getOne(new Long(1));

        ClassRoom classRoom = new ClassRoom();
        classRoom.setName("My test class");
        classRoom.setState(classState);
        classRoomRepository.save(classRoom);

        ClassRoom classRoom2 = classRoomRepository.getOne(new Long(1));
        assertEquals(classRoom.getName(), classRoom2.getName());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void changeClassRoomStatus() {
        ClassRoom classRoom = classRoomRepository.getOne(new Long(1));
        ClassRoomState classState = classRoomStatusRepository.getOne(new Long(2));

        classRoom.setState(classState);

        classRoomRepository.save(classRoom);

        ClassRoom classRoom2 = classRoomRepository.getOne(new Long(1));
        assertEquals(classRoom2.getState().getName(), classState.getName());
    }

//    @Test
    public void contextLoads() {
    }

}
