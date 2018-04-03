/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.dmo.User;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import gr.aegean.eIdEuSmartClass.model.service.RaspberryInterface;

/**
 *
 * @author nikos
 */
@Controller
public class RestControllers {

    private static final Logger log = LoggerFactory.getLogger(RestControllers.class);
    private final Set<String> SUPER_USERS;
    private final static String INACTIVE = "inactive";

    @Autowired
    private UserService userServ;

    @Autowired
    private ClassRoomService classroomServ;

    @Autowired
    private ActiveDirectoryService adServ;

    @Autowired
    private RaspberryInterface rasbServ;

    @Autowired
    private MailService mailServ;

    public RestControllers() {
        SUPER_USERS = new HashSet<String>();
        this.SUPER_USERS.add("coordinator");
        this.SUPER_USERS.add("admin");
        this.SUPER_USERS.add("superadmin");
    }

    /**
     * Adds a new user to the database and to the Active Directory by making an
     * appropriate API call
     *
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

    @RequestMapping(value = "validateCode", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    BaseResponse doorCodeValidity(@RequestParam(value = "roomId", required = true) String roomId,
            @RequestParam(value = "qrCode", required = true) String qrCode) {
        
        if (classroomServ.getValidCodeByName(roomId) != null && classroomServ.getValidCodeByName(roomId).contains(qrCode)) {
            
            //TODO check time less that 10:00pm
            //check if time issues is less taht 4hours
            // chake that when it was created it was less thean 10 min
            
            return new BaseResponse(BaseResponse.SUCCESS);
        } else {
            return new BaseResponse(BaseResponse.FAILED);
        }
    }

    /**
     * Changes the given room status to the provided one if the new status is
     * close (inActive) then an API calle is made to a raspberry to close the
     * lights etc.
     *
     * @param roomName
     * @param principal
     * @return
     */
    @RequestMapping(value = "updateclass", method = {RequestMethod.POST, RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    BaseResponse sendCloseRoom(@RequestParam(value = "roomName", required = true) String roomName,
            @RequestParam(value = "roomStatus", required = true) String status,
            Principal principal) {
        String userEid = principal.getName();
        User user = userServ.findByEid(userEid);
        if (user != null && SUPER_USERS.contains(user.getRole().getName())) {
            try {
                if (status.equals(INACTIVE)) {
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

}
