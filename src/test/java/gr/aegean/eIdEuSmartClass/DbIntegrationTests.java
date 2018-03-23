package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.dao.ActiveCodeRepository;
import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dao.RoomStatesRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.dao.UserRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCodePK;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.RoomState;
import java.time.LocalDate;
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
    private ClassRoomRepository classRepo;

    @Autowired
    private RoomStatesRepository statesRepo;

    @Autowired
    private ActiveCodeRepository activeRepo;

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

    @Test
    public void saveRoomState() {
        RoomState state = new RoomState();
        state.setName("testState");
        statesRepo.save(state);
    }

    @Test
    public void saveClassRomm() {
        ClassRoom room = new ClassRoom();
        room.setName("testName");
//        Optional<RoomState> state2 = statesRepo.findById(new Long(1));
        Optional<RoomState> state2 = statesRepo.findByName("Restricted");
//        List<RoomState> states = statesRepo.findAll();
        if (state2.isPresent()) {
            room.setRoomStates(state2.get());
        }
        classRepo.save(room);
    }

    @Test
    public void testActiveCode() {
        ActiveCode ac = new ActiveCode();
        Role r = new Role("test");
        Gender g = new Gender("n/a");
        LocalDate birthday = LocalDate.now();
        User user = new User("eidas-id3", "name3", "surname", birthday, r, g);

        ClassRoom room = new ClassRoom();
        room.setName("testName2");
        Optional<RoomState> state2 = statesRepo.findByName("Restricted");
        if (state2.isPresent()) {
            room.setRoomStates(state2.get());
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

    }

    @Test
    public void contextLoads() {
    }

}
