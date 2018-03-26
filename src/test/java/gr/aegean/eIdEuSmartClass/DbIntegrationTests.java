package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomStateRepository;
import gr.aegean.eIdEuSmartClass.model.dao.GenderRepository;
import gr.aegean.eIdEuSmartClass.model.dao.RoleRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.dao.UserRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoomState;
import gr.aegean.eIdEuSmartClass.model.dao.ActiveCodeRepository;
import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.dao.UserRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCodePK;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import java.time.LocalDate;
import javax.transaction.Transactional;
import java.util.Optional;
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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private ClassRoomStateRepository classRoomStateRepository;

    @Autowired
    private ClassRoomRepository classRepo;

    @Autowired
    private ActiveCodeRepository activeRepo;

    @Test
    @Transactional
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
    public void testFindUserByEidas() {
        Role r = new Role("test");
        Gender g = new Gender("n/a");
        LocalDate birthday = LocalDate.now();
        User user = new User("eidas-id4", "name2", "surname", birthday, r, g);
        System.out.println(user.toString());
        userRepository.save(user);
        User user3 = userRepository.findFirstByEIDASId("eidas-id4");
        assertEquals(user3.geteIDAS_id(), "eidas-id4");

    }

    @Test
    public void saveRoomState() {
        ClassRoomState state = new ClassRoomState();
        state.setName("testState");
        classRoomStateRepository.save(state);
    }

    @Test
    @Transactional
    public void saveClassRomm() {
        ClassRoom room = new ClassRoom();
        room.setName("testName");
//        Optional<RoomState> state2 = statesRepo.findById(new Long(1));
        Optional<ClassRoomState> state2 = classRoomStateRepository.findByName("Restricted");
//        List<RoomState> states = statesRepo.findAll();
        if (state2.isPresent()) {
            room.setState(state2.get());
        }
        classRepo.save(room);
    }

    @Test
    @Transactional
    public void testActiveCode() {
        ActiveCode ac = new ActiveCode();
        Role r = roleRepository.findFirstByName(Role.UNREGISTERED);
        Gender g = genderRepository.findFirstByName(Gender.UNSPECIFIED);
        LocalDate birthday = LocalDate.now();
        User user = new User("eidas-id3", "name3", "surname", birthday, r, g);

        ClassRoom room = new ClassRoom();
        room.setName("testName2");
        Optional<ClassRoomState> state2 = classRoomStateRepository.findByName("Restricted");
        if (state2.isPresent()) {
            room.setState(state2.get());
        }

        classRepo.save(room);
        userRepository.save(user);
        ac.setGrantedAt(LocalDate.now());
        ac.setContent("testContent");

        ActiveCodePK key = new ActiveCodePK();
        key.setClassRoom(room);
        key.setUser(user);

        ac.setId(key);
        activeRepo.save(ac);
        assertEquals(activeRepo.getContentFromClassRoom("testName2"), "testContent");

    }

    @Test
    @Transactional
    public void createClassroom() {
        ClassRoomState classState = classRoomStateRepository.getOne(new Long(1));

        ClassRoom classRoom = new ClassRoom();
        classRoom.setName("My test class");
        classRoom.setState(classState);
        classRoomRepository.save(classRoom);

        ClassRoom classRoom2 = classRoomRepository.getOne(classRoom.getId()             );
        assertEquals(classRoom.getName(), classRoom2.getName());
    }

    @Test
    @Transactional
    public void changeClassRoomStatus() {
        //TODO:: write a classroom first
        ClassRoom classRoom = classRoomRepository.getOne(new Long(1));
        ClassRoomState classState = classRoomStateRepository.getOne(new Long(2));

        classRoom.setState(classState);

        classRoomRepository.save(classRoom);

        ClassRoom classRoom2 = classRoomRepository.getOne(new Long(1));
        assertEquals(classRoom2.getState().getName(), classState.getName());
    }

//    @Test
    public void contextLoads() {
    }

}
