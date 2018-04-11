/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCodePK;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.SkypeRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import gr.aegean.eIdEuSmartClass.model.service.ActiveDirectoryService;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.ConfigPropertiesServices;
import gr.aegean.eIdEuSmartClass.model.service.GenderService;
import gr.aegean.eIdEuSmartClass.model.service.MailService;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import gr.aegean.eIdEuSmartClass.model.service.SkypeRoomService;
import gr.aegean.eIdEuSmartClass.model.service.TokenService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.enums.GenderEnum;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.generators.QRGenerator;
import gr.aegean.eIdEuSmartClass.utils.generators.UtilGenerators;
import gr.aegean.eIdEuSmartClass.utils.pojo.ADResponse;
import gr.aegean.eIdEuSmartClass.utils.pojo.BaseResponse;
import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.wrappers.UserWrappers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private ClassRoomService classServ;

    @Autowired
    private UserService userServ;

    @Autowired
    private GenderService genServ;

    @Autowired
    private RoleService roleServ;

    @Autowired
    private MailService mailServ;

    @Autowired
    private SkypeRoomService skypeRoomServ;

    @Autowired
    private ActiveCodeService activeServ;

    @Autowired
    private ActiveDirectoryService adServ;

    public final static String TOKEN_NAME = "access_token";
    private final static Logger log = LoggerFactory.getLogger(ViewControllers.class);

    /**
     * Returns the landing view containing options to register, gain physical
     * access, skype room, team
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"landingTest"})
    public String landingTest(Model model) {
        model.addAttribute("classRooms", classServ.findAll());
        model.addAttribute("skypeRooms", skypeRoomServ.getAllRooms());
        return "landingViewTest";
    }

    /**
     * Returns the landing view containing options to register, gain physical
     * access, skype room, team
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"landing", "/", ""})
    public String landing(Model model) {
        model.addAttribute("classRooms", classServ.findAll());
        model.addAttribute("skypeRooms", skypeRoomServ.getAllRooms());
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
     * @param jwtCookie
     * @param typeCookie
     * @param req
     * @param principal
     * @param model
     * @param redirectAttrs
     * @return
     */
    @RequestMapping(value = {"eIDASSuccess"})
    public String login(@CookieValue(value = TOKEN_NAME, required = true) String jwtCookie,
            @CookieValue(value = "type", required = true) String typeCookie,
            HttpServletRequest req, Principal principal, Model model, RedirectAttributes redirectAttrs) {

        Optional<User> user = userServ.findByEid(principal.getName());
        if (!user.isPresent()) {
            try {
                String decodedToken = tokenServ.decode(jwtCookie);
                FormUser fuser = UserWrappers.wrapDecodedJwtEidasUser(decodedToken);
                user = Optional.of(UserWrappers.wrapFormUserToDBUser(fuser, roleServ, genServ));
                if (user.isPresent()) {
                    user.get().setEmail("n/a");
                    if (genServ.getGenderByName(GenderEnum.UNSPECIFIED.gender()).isPresent()) {
                        user.get().setGender(genServ.getGenderByName(GenderEnum.UNSPECIFIED.gender()).get());
                    }
                    userServ.saveUser(user.get());
                    redirectAttrs.addFlashAttribute("user", fuser);

                } else {
                    log.info("Could not wrap user from eIDAS success");
                }
                return "redirect:/register";
            } catch (UnsupportedEncodingException ex) {
                log.info("ERROR " + ex.getMessage());
            } catch (IOException ex) {
                log.info("ERROR " + ex.getMessage());
            }
        }
        if (user.isPresent() && user.get().getRole().getName().equals(RolesEnum.UNIDENTIFIED.role())) {
            redirectAttrs.addFlashAttribute("user", user.get());
            return "redirect:/pending";
        }

        if (typeCookie.contains("team")) {
            return "redirect:/team";
        }

        if (typeCookie.contains("skype")) {
            return "redirect:/skype";
        }

        if (typeCookie.contains("physical")) {
            return "redirect:/roomaccess";
        }

        return "errorPage";
    }

    @RequestMapping(value = {"pending"})
    public String pending() {
        return "pendingView";
    }

    @RequestMapping(value = {"register"})
    public String register() {
        return "registerView";
    }

    @RequestMapping(value = {"profile", "edit"})
    public String editProfile(Principal principal, Model model) {
        Optional<User> user = userServ.findByEid(principal.getName());
        if (user.isPresent()) {
            try {
                model.addAttribute("user", UserWrappers.wrapDBUsertoFormUser(user.get()));
                return "profile";
            } catch (Error e) {
                log.info("ERROR " + e.getMessage());
            }
        }

        return "error";
    }

    @RequestMapping(value = {"team"})
    public String team(Principal principal, Model model) {
        Optional<User> user = userServ.findByEid(principal.getName());
        //TODO  insert User into the Group “Teams” of the Azure AD  add2Grpup user ID is the new AD-USER-ID field in the db
        if (user.isPresent()) {
            String teamName = user.get().getName();
            String teamPass = UtilGenerators.generateRandomPass(10);
            mailServ.prepareAndSendTeamCredentials(user.get().getEmail(), teamName, teamPass, user.get().getName() + " " + user.get().getSurname());
            model.addAttribute("TeamURL", propServ.getPropByName("TEAM_URL"));
        }
        return "teamView";
    }

    @RequestMapping(value = {"skype"})
    public String skype(Model model,
            Principal principal,
            @CookieValue(value = "type", required = true) String typeCookie
    ) {
        Optional<User> user = userServ.findByEid(principal.getName());
        //TODO insert user to Group “SkypeForBusiness” of the Azure AD
        if (user.isPresent()) {
            String roomId = typeCookie.split("-")[1];
            SkypeRoom room = skypeRoomServ.getRoomFromId(roomId);
            if (room != null) {
                model.addAttribute("room", room);
                mailServ.prepareAndSendSkypeLink(user.get().getEmail(), user.get().getName() + user.get().getSurname(), room.getUrl());
            }
        }
        return "skypeView";
    }

    @RequestMapping(value = {"roomaccess"})
    public String physical(Model model,
            Principal principal,
            @CookieValue(value = "type", required = true) String typeCookie) {

        Optional<User> user = userServ.findByEid(principal.getName());
        String roomId = typeCookie.split("-")[1];
        Optional<ClassRoom> room = classServ.getRoomById(roomId);

        if (room.isPresent()
                && user.isPresent()
                && !user.get().getRole().getName().equals(RolesEnum.BLACKLISTED.role())) {
            //TODO Is inserted into the Group “UAegean-HPClass” of the Azure AD 
            ActiveCode ac = new ActiveCode();
            ac.setContent(UtilGenerators.generateRandomPIN(4));
            ac.setGrantedAt(LocalDateTime.now());
            ActiveCodePK key = new ActiveCodePK(user.get(), room.get());
            ac.setId(key);
            activeServ.save(ac);
            model.addAttribute("pin", ac.getContent());
            String qrImgPath;
            try {
                qrImgPath = QRGenerator.generateQR(roomId, user.get().geteIDAS_id(), ac.getContent());
                model.addAttribute("qrPath", qrImgPath);
            } catch (IOException ex) {
                log.info("Error " + ex.getMessage());
            }
        }

        return "physicalView";
    }

    /**
     * Adds a new user to the database and to the Active Directory by making an
     * appropriate API call ***TODO desired role!!!!! sendInvite AD ***ROLE not
     * specified in AD so not valid so ignore the part about user role here in
     * the API call***
     *
     * add at smartclass db field AD-USER-ID response from API call *** @return
     */
    @RequestMapping(value = "createUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String createUser(@ModelAttribute("user") FormUser user) {
        try {
            String safeEid = DigestUtils.md5DigestAsHex(user.getEid().getBytes(StandardCharsets.UTF_8));
            String engGiveName = user.getCurrentGivenName().split(",").length>1?user.getCurrentGivenName().split(",")[1]:user.getCurrentGivenName();
            String engFamilyName = user.getCurrentFamilyName().split(",").length>1?user.getCurrentFamilyName().split(",")[1]:user.getCurrentFamilyName();
            ADResponse adResp = adServ.createUser(engGiveName + engFamilyName,
                    safeEid, engGiveName, engFamilyName, safeEid, "test1234###");

            BaseResponse resp = userServ.saveOrUpdateUser(user.getEid(), user.getCurrentGivenName(), user.getCurrentFamilyName(),
                    GenderEnum.UNSPECIFIED.gender(), user.getDateOfBirth(), user.getEmail(),
                    user.getMobile(), user.getAffiliation(), user.getCountry(), adResp.getId());
            if (resp.getStatus().equals("OK")) {
                mailServ.prepareAndSend(user.getEmail(), "test", user.getProfileName());
                return "updateSuccessView";
            }
        } catch (IOException ex) {
            log.info("ERROR", ex);
        }
        return "error";
    }

    /**
     * updates the attributes of the user and makes the corresponding API call
     * to add the user to the active directory
     */
    @RequestMapping(value = "updateUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String updateUser(@ModelAttribute("user") FormUser user) {
//        try {
//            adServ.createUser("smartclassguest1@outlook.com");
//        } catch (IOException ex) {
//            log.info("ERROR", ex);
//        }
        Optional<User> oldUser = userServ.findByEid(user.getEid());
        String gender = GenderEnum.UNSPECIFIED.gender();

        if (oldUser.isPresent()) {
            gender = oldUser.get().getGender().getName();
        }

        BaseResponse resp = userServ.saveOrUpdateUser(user.getEid(), user.getCurrentGivenName(), user.getCurrentFamilyName(),
                gender, user.getDateOfBirth(), user.getEmail(), user.getMobile(), user.getAffiliation(), user.getCountry(), "");
        if (resp.getStatus().equals("OK")) {
            return "updateSuccessView";
        }
        return "error";
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
