/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.dao.UserRepository;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.pojo.RasberyrResponse;
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

    @Autowired
    private UserService userServ;

    @Autowired
    private ClassRoomService roomServ;

    @RequestMapping(value = "createUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    RasberyrResponse createUser(@RequestParam(value = "eIDASid", required = true) String eIDASid,
            @RequestParam(value = "name", required = true) String name, @RequestParam(value = "surname", required = true) String surname,
            @RequestParam(value = "gender", required = true) String gender,
            @RequestParam(value = "dateOfBirth", required = true) String dateOfBirth) {
        //TODO call microservice that writes in the Active Directory server
        return userServ.saveUser(eIDASid, name, surname, gender, dateOfBirth);
    }

    @RequestMapping(value = "doorCodeValidity", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    RasberyrResponse doorCodeValidity(@RequestParam(value = "roomId", required = true) String roomId,
            @RequestParam(value = "qrCode", required = true) String qrCode) {
        if (roomServ.getValidCodeByName(roomId).equals(qrCode)) {
            return new RasberyrResponse(RasberyrResponse.SUCCESS);
        } else {
            return new RasberyrResponse(RasberyrResponse.FAILED);

        }
    }

}
