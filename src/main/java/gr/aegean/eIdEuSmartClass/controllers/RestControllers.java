/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.dao.RoleRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoomState;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import gr.aegean.eIdEuSmartClass.model.service.ActiveDirectoryService;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.MailService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.pojo.BaseResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import gr.aegean.eIdEuSmartClass.model.service.RaspberryInterface;
import gr.aegean.eIdEuSmartClass.utils.enums.RoomStatesEnum;
import gr.aegean.eIdEuSmartClass.utils.validators.ValidateRoomCode;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nikos
 */
@RestController
public class RestControllers {

    private static final Logger log = LoggerFactory.getLogger(RestControllers.class);
    private final Set<String> SUPER_USERS;
    private final static String INACTIVE = "inactive";

    @Autowired
    private UserService userServ;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private ClassRoomService classroomServ;

    @Autowired
    private ActiveDirectoryService adServ;

    @Autowired
    private RaspberryInterface rasbServ;

    @Autowired
    private MailService mailServ;

    @Autowired
    private ActiveCodeService activeServ;

    public RestControllers() {
        SUPER_USERS = new HashSet<String>();
        this.SUPER_USERS.add("coordinator");
        this.SUPER_USERS.add("admin");
        this.SUPER_USERS.add("superadmin");
    }

    /**
     * Adds a new user to the database and to the Active Directory by making an
     * appropriate API call ***TODO desired role!!!!!
     *
     ****
     * @return
     */
    @RequestMapping(value = "createUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    BaseResponse createUser(@ModelAttribute("user") FormUser user) {
        try {
            adServ.registerUser("smartclassguest1@outlook.com");
        } catch (IOException ex) {
            log.error("ERROR", ex);
        }
        BaseResponse resp = userServ.saveUser(user.getEid(), user.getCurrentGivenName(), user.getCurrentFamilyName(),
                "Unspecified", user.getDateOfBirth(), user.getEmail(), user.getMobile(), user.getAffiliation(), user.getCountry());
        if (resp.getStatus().equals("OK")) {
            mailServ.prepareAndSend(user.getEmail(), "test", user.getProfileName());
        }
        return resp;
    }

    /**
     * checks taht the give qr code/pin was belongs to the codes issued for the
     * give roomID that the room is ACTIVE and that the code was issued in the
     * last 4 hours. and that it is not past 22:00
     *
     * Also, if the key is not used within 5 mins of creation it is made
     * inactive!!! the admin keys codes do not expire!!!
     *
     * @param roomId
     * @param qrCode
     * @return
     */
    @RequestMapping(value = "validateCode", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    BaseResponse doorCodeValidity(@RequestParam(value = "roomId", required = true) String roomId,
            @RequestParam(value = "qrCode", required = true) String qrCode) {
        //TODO send email
        List<String> roomCodes = classroomServ.getValidCodeByName(roomId);

        ClassRoomState state = classroomServ.getRoomStatus(roomId);
        if (!state.getName().equals(RoomStatesEnum.INACTIVE.state())
                && roomCodes != null && roomCodes.contains(qrCode)
                && ValidateRoomCode.validateCode(qrCode, activeServ, LocalDateTime.now())) {
            return new BaseResponse(BaseResponse.SUCCESS);
        }
        return new BaseResponse(BaseResponse.FAILED);
    }

    /**
     * Changes the given room status to the provided one if the new status is
     * close (inActive) then an API calle is made to a raspberry to close the
     * lights etc. Can only be used if user has role:
     * admin,superadmin,coordinator
     *
     * @param roomName
     * @param principal
     * @return
     */
    @RequestMapping(value = "updateclass", method = {RequestMethod.POST, RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    BaseResponse updateClassRoom(@RequestParam(value = "roomName", required = true) String roomName,
            @RequestParam(value = "roomStatus", required = true) String status,
            Principal principal) {
        String userEid = principal.getName();
        User user = userServ.findByEid(userEid);
        if (user != null) {
            try {
                if (status.equals(RoomStatesEnum.INACTIVE.state())) {
                    rasbServ.requestCloseRoom(roomName);
                }
                classroomServ.setRoomStatusByStateName(status, roomName);
                return new BaseResponse(BaseResponse.SUCCESS);
            } catch (Exception err) {
                log.info("ERROR: " + err.getMessage());
            }
        }
        return new BaseResponse(BaseResponse.FAILED);
    }

    @RequestMapping(value = "getUsersByRole", method = {RequestMethod.GET})
    public @ResponseBody
    Set<User>
            getUsersByRole(@RequestParam(value = "role", required = true) String role) {
        Role roleDb = roleRepo.findByName(role).get();
        
        return roleDb.getUsers();
    }

}
