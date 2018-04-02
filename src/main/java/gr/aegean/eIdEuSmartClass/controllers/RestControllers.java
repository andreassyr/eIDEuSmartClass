/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ActiveDirectoryService;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.RasberryInterface;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.pojo.RasberyrResponse;
import java.io.IOException;
import java.net.ProtocolException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nikos
 */
@Controller
public class RestControllers {

    private static final Logger log = LoggerFactory.getLogger(RestControllers.class);
    private final Set<String> SUPER_USERS;

    @Autowired
    private UserService userServ;

    @Autowired
    private ClassRoomService roomServ;

    @Autowired
    private ActiveDirectoryService asServ;

    @Autowired
    private RasberryInterface rasbServ;

    public RestControllers() {
        SUPER_USERS = new HashSet<String>();
        this.SUPER_USERS.add("coordinator");
        this.SUPER_USERS.add("admin");
        this.SUPER_USERS.add("superadmin");
    }

    @RequestMapping(value = "createUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    RasberyrResponse createUser(@RequestParam(value = "eIDASid", required = true) String eIDASid,
            @RequestParam(value = "name", required = true) String name, @RequestParam(value = "surname", required = true) String surname,
            @RequestParam(value = "gender", required = true) String gender,
            @RequestParam(value = "dateOfBirth", required = true) String dateOfBirth) {
        try {
            //TODO call microservice that writes in the Active Directory server
            asServ.registerUser("smartclassguest1@outlook.com");
        } catch (IOException ex) {
            log.error("ERROR", ex);
        }
        return userServ.saveUser(eIDASid, name, surname, gender, dateOfBirth);
    }

    @RequestMapping(value = "validateQR", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    RasberyrResponse doorCodeValidity(@RequestParam(value = "roomId", required = true) String roomId,
            @RequestParam(value = "qrCode", required = true) String qrCode) {
        if (roomServ.getValidCodeByName(roomId) != null && roomServ.getValidCodeByName(roomId).contains(qrCode)) {
            return new RasberyrResponse(RasberyrResponse.SUCCESS);
        } else {
            return new RasberyrResponse(RasberyrResponse.FAILED);
        }
    }

    @RequestMapping(value = "changeRoomStatusRasb", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    RasberyrResponse changeRoomStatus(@RequestParam(value = "roomName", required = true) String roomName,
            @RequestParam(value = "statusName", required = true) String statusName) {
        if (roomServ.setRoomStatusByStateName(statusName, roomName)) {
            return new RasberyrResponse(RasberyrResponse.SUCCESS);
        }
        return new RasberyrResponse(RasberyrResponse.FAILED);
    }

    @RequestMapping(value = "requestCloseRoom", method = {RequestMethod.POST,RequestMethod.GET }, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    String sendCloseRoom(@RequestParam(value = "roomName", required = true) String roomName, Principal principal) {
        String userEid = principal.getName();
        User user = userServ.findByEid(userEid);
        if (user != null && SUPER_USERS.contains(user.getRole().getName())) {
            try {
                rasbServ.requestCloseRoom(roomName);
                return "OK";
            } catch (Exception err) {
                log.info("ERROR: " + err.getMessage());
            }
        }
        return "NOK";
    }

    @RequestMapping(value = {"registerUser"}, method = {RequestMethod.POST})
    public @ResponseBody
    String successPage(@RequestParam(value = "email", required = true) String email) {

        return "OK";
    }

}
