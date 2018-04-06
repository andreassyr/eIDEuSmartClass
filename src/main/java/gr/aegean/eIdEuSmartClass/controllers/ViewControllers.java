/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ConfigPropertiesServices;
import gr.aegean.eIdEuSmartClass.model.service.GenderService;
import gr.aegean.eIdEuSmartClass.model.service.MailService;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import gr.aegean.eIdEuSmartClass.model.service.TokenService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.enums.GenderEnum;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.wrappers.UserWrappers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nikos
 */
@Controller
public class ViewControllers {

    @Autowired
    private ConfigPropertiesServices propServ;

    @Autowired
    private TokenService tokenServ;

    @Autowired
    private ClassRoomRepository classRepo;

    @Autowired
    private UserService userServ;

    @Autowired
    private GenderService genServ;

    @Autowired
    private RoleService roleServ;

    @Autowired
    private MailService mailServ;

    public final static String TOKEN_NAME = "access_token";
    private final static Logger log = LoggerFactory.getLogger(ViewControllers.class);

//    @RequestMapping(value = {"register"})
//    public String register(HttpServletRequest request, Model model, Principal principal) throws UnsupportedEncodingException, IOException {
//
//        FormUser user = UserWrappers.wrapDecodedJwtEidasUser(tokenServ.decode(principal.toString()));
//
//        model.addAttribute("user", user);
//        return "registerView";
//    }
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
     * Gets the user from the JWT token, if user is not found the he/she is
     * added with role unidentified and user is presented with a page stating
     * that activation is pending (emails are sent)
     *
     * If the user is found then the cookie type is used to check the type of
     * the login physical (QR), skype, or team and the browser is redirected to
     * the appropriate view
     *
     * @return
     */
    @RequestMapping(value = {"eIDASSuccess"})
    public String login(@CookieValue(value = TOKEN_NAME, required = true) String jwtCookie,
            @CookieValue(value = "type", required = true) String typeCookie,
            HttpServletRequest req, Principal principal, Model model, RedirectAttributes redirectAttrs) {

        User user = userServ.findByEid(principal.getName());
        if (user == null) {
            try {
                String decodedToken = tokenServ.decode(jwtCookie);
                FormUser fuser = UserWrappers.wrapDecodedJwtEidasUser(decodedToken);
                user = UserWrappers.wrapFormUserToDBUser(fuser, roleServ, genServ);
                user.setEmail("n/a");
                user.setGender(genServ.getGenderByName(GenderEnum.UNSPECIFIED.gender()));
                userServ.saveUser(user);
                redirectAttrs.addFlashAttribute("user", fuser);

                return "redirect:/register";
            } catch (UnsupportedEncodingException ex) {
                log.info("ERROR " + ex.getMessage());
            } catch (IOException ex) {
                log.info("ERROR " + ex.getMessage());
            }
        }
        if (user.getRole().getName().equals(RolesEnum.UNIDENTIFIED.role())) {
            redirectAttrs.addFlashAttribute("user", user);
            return "redirect:/pending";
        }

        if (typeCookie.equals("Team")) {
            return "teamView";
        }

        if (typeCookie.equals("skype")) {
            return "skypeView";
        }

        return "loggedInView";
    }

    @RequestMapping(value = {"pending"})
    public String pending() {
        return "pendingView";
    }

    @RequestMapping(value = {"register"})
    public String register() {
        return "registerView";
    }

    @RequestMapping(value = {"skype"})
    public String skype() {
        return "skypeView";
    }
    
    @RequestMapping(value = {"team"})
    public String team() {
        return "teamView";
    }
    
    @RequestMapping(value = {"physical"})
    public String physical() {
        return "physical";
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
