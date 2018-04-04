/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.service.ConfigPropertiesServices;
import gr.aegean.eIdEuSmartClass.model.service.TokenService;
import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.wrappers.UserWrappers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nikos
 */
@Controller
public class ViewControllers {

    @Autowired
    ConfigPropertiesServices propServ;

    @Autowired
    TokenService tokenServ;

    @Autowired
    ClassRoomRepository classRepo;

    public final static String TOKEN_NAME = "access_token";
    private final static Logger LOG = LoggerFactory.getLogger(ViewControllers.class);

//    @RequestMapping(value = {"landing", "/", ""})
//    public String landingOld(@CookieValue(value = TOKEN_NAME, required = false) String jwtCookie,
//            @CookieValue(value = "flow", required = false) String flowCookie,
//            HttpServletRequest req) throws UnsupportedEncodingException, IOException, IOException {
//        if (flowCookie != null) {
//            if (flowCookie.equals("login")) {
//                return "redirect:/loggedIn";
//            } else {
//                return "redirect:/register";
//            }
//        }
//        return "landingView";
////    }
//    @RequestMapping(value = {"login"})
//    public String loginOrRegister() {
//        return "loginView";
//    }
//    @RequestMapping(value = {"loggedIn"})
//    public String loggedIn() {
//        return "loggedInView";
//    }
    @RequestMapping(value = {"register"})
    public String register(HttpServletRequest request, Model model, Principal principal) throws UnsupportedEncodingException, IOException {

        FormUser user = UserWrappers.wrapDecodedJwtEidasUser(tokenServ.decode(principal.toString()));

        model.addAttribute("user", user);
        return "registerView";
    }

    /**
     * Returns the landing view containing options to register, gain physical
     * access, skype room, team
     *
     * @return
     */
    @RequestMapping(value = {"landing", "/", ""})
    public String landing(Model model) {
        model.addAttribute("classRooms", classRepo.findAll());
        return "landingView";
    }

    /**
     * redirection after success full eidas login depending on the type of the
     * cookie returns the user to the view physical (QR), skype, or team
     *
     * @return
     */
    @RequestMapping(value = {"eIdasSuccess"})
    public String login(@CookieValue(value = TOKEN_NAME, required = true) String jwtCookie,
            @CookieValue(value = "type", required = true) String typeCookie,
            HttpServletRequest req, Principal principal) {

        principal.getName(); //eID;

        return "loggedInView";
    }

    /**
     * private admin view. Can only be accessed by admin users and allows to
     * edit users
     *
     *
     * @return
     */
    public String admin() {
        return null;
    }
}
