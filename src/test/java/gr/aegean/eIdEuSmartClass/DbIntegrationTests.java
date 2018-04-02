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
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
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

    
    @Autowired 
    private ClassRoomService roomServ;
    
    
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
        Role r = new Role("test");
        Gender g = new Gender("n/a");
        LocalDate birthday = LocalDate.now();
        User user = new User("eidas-id3", "name3", "surname", birthday, r, g);
        
        Role r2 = new Role("test");
        Gender g2 = new Gender("n/a");
        User user2 = new User("eidas-id5", "name5", "surname5", birthday, r2, g2);
        userRepository.save(user);
        userRepository.save(user2);

        ClassRoom room = new ClassRoom();
        room.setName("testName2");
        Optional<RoomState> state2 = statesRepo.findByName("Restricted");
        if (state2.isPresent()) {
            room.setRoomStates(state2.get());
        }

        classRepo.save(room);

        ActiveCode ac = new ActiveCode();
        ac.setGrantedAt(LocalDate.now());
        ac.setContent("testContent");
        ActiveCodePK key = new ActiveCodePK();
        key.setClassRoom(room);
        key.setUser(user);
        ac.setId(key);
        activeRepo.save(ac);

        
        ActiveCode ac2 = new ActiveCode();
        ac2.setGrantedAt(LocalDate.now());
        ac2.setContent("testContent2");
        ActiveCodePK key2 = new ActiveCodePK();
        key2.setClassRoom(room);
        key2.setUser(user2);
        ac2.setId(key2);
        activeRepo.save(ac2);

        assertEquals(activeRepo.getContentFromClassRoom("testName2").contains("testContent"), true);
//        assertEquals(activeRepo.getContentFromClassRoom("testName2").contains("testContent2"), true);

    }

    @Test
    public void updateClassRoomState(){
        Optional<RoomState> inactive = statesRepo.findByName("Inactive");
        roomServ.setRoomStatusByStateName("inactive", "testName");
        assertEquals(classRepo.findByName("testName").getRoomStates().getName(),"Inactive");
    
    }
    
    
    @Test
    public void contextLoads() {
    }

}
