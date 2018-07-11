/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCodePK;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoomState;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.SkypeRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.Teams;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import gr.aegean.eIdEuSmartClass.model.service.ActiveDirectoryService;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.ConfigPropertiesServices;
import gr.aegean.eIdEuSmartClass.model.service.GenderService;
import gr.aegean.eIdEuSmartClass.model.service.MailService;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import gr.aegean.eIdEuSmartClass.model.service.SkypeRoomService;
import gr.aegean.eIdEuSmartClass.model.service.TeamsService;
import gr.aegean.eIdEuSmartClass.model.service.TokenService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.enums.EmailTypes;
import gr.aegean.eIdEuSmartClass.utils.enums.GenderEnum;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.enums.RoomStatesEnum;
import gr.aegean.eIdEuSmartClass.utils.generators.QRGenerator;
import gr.aegean.eIdEuSmartClass.utils.generators.UtilGenerators;
import gr.aegean.eIdEuSmartClass.utils.pojo.ADHelpers;
import gr.aegean.eIdEuSmartClass.utils.pojo.BaseResponse;
import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.wrappers.UserWrappers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private TeamsService teamServ;

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
        model.addAttribute("teams", teamServ.findAll());
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
    public String landing(Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        model.addAttribute("classRooms", classServ.findAll());
        model.addAttribute("skypeRooms", skypeRoomServ.getAllRooms());
        model.addAttribute("teams", teamServ.findAll());
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        model.addAttribute("loginPath",propServ.getPropByName("LOGIN_URL"));
        return "landingView";
    }

    @RequestMapping(value = {"smart-class"})
    public String smartClass(Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        return "smartclassView";
    }

    /**
     *
     * TODO handle empty JWT!!! Gets the user from the JWT token, if user is not
     * found the he/she is added with role unidentified and user is presented
     * with a page stating that activation is pending (emails are sent)
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
    public String login(@CookieValue(value = TOKEN_NAME, required = false) String jwtCookie,
            @CookieValue(value = "type", required = false) String typeCookie,
            HttpServletRequest req, Principal principal, Model model, RedirectAttributes redirectAttrs) {

        if (principal != null) {
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

                    if (typeCookie.contains("register")) {
                        redirectAttrs.addFlashAttribute("message", "Thank you for registering with the service, please complete it by filling in the following info");
                    } else {
                        redirectAttrs.addFlashAttribute("message", "In order to use the service you first need to register. Please fill in the following form");
                    }
                    mailServ.sendMailToAdmin(user.get().getEngSurname());
                    return "redirect:/register";
                } catch (UnsupportedEncodingException ex) {
                    log.info("ERROR ", ex);
                } catch (IOException ex) {
                    log.info("ERROR ", ex);
                }
            }
            if (user.isPresent() && user.get().getRole().getName().equals(RolesEnum.UNIDENTIFIED.role())) {
                redirectAttrs.addFlashAttribute("user", user.get());
                return "redirect:/pending";
            }

            if (typeCookie.contains("team")) {
                return "redirect:/team";
            }

            if (typeCookie.contains("skypeJoin")) {
                return "redirect:/selectConf";
            }

            if (typeCookie.contains("skype")) {
                return "redirect:/skype";
            }

            if (typeCookie.contains("physical")) {
                return "redirect:/roomaccess";
            }

            if (typeCookie.contains("admin")) {
                return "redirect:/admin";
            }

            if (typeCookie.contains("register")) {
                return "redirect:/";
            }

        }
        return "redirect:/error";
    }

    @RequestMapping(value = {"pending"})
    public String pending(Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));

        return "pendingView";
    }

    @RequestMapping(value = {"register"})
    public String register(Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        return "registerView";
    }

    @RequestMapping(value = {"profile", "edit"})
    public String editProfile(Principal principal, Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        Optional<User> user = userServ.findByEid(principal.getName());
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));

        if (user.isPresent()) {
            try {
                model.addAttribute("user", UserWrappers.wrapDBUsertoFormUser(user.get()));
                return "profile";
            } catch (Error e) {
                log.info("ERROR ", e);
            }
        }

        return "error";
    }

    @RequestMapping(value = {"selectTeam"})
    public String teamSelect(Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        List<Teams> teams = teamServ.findAll();
        model.addAttribute("teams", teams);
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        return "selectTeam";
    }

    @RequestMapping(value = {"selectConf"})
    public String skypeSelect(Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        List<SkypeRoom> rooms = skypeRoomServ.getAllRooms();
        model.addAttribute("rooms", rooms);
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        return "selectConf";
    }

    @RequestMapping(value = {"skype"})
    public String skype(Model model,
            Principal principal,
            @CookieValue(value = "type", required = true) String typeCookie,
            @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie
    ) {
        Optional<User> user = userServ.findByEid(principal.getName());
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        // insert user to Group “SkypeForBusiness” of the Azure AD
        if (user.isPresent()) {
            String roomId = typeCookie.split("-")[1];
            SkypeRoom room = skypeRoomServ.getRoomFromId(roomId);
            if (room != null) {
                model.addAttribute("room", room);
//                String mailName = StringUtils.isEmpty(user.get().getEngName()) ? user.get().getCurrentGivenName() : user.get().getEngName();
//                String mailSurname = StringUtils.isEmpty(user.get().getEngSurname()) ? user.get().getCurrentFamilyName() : user.get().getEngSurname();
//                if (StringUtils.isEmpty(user.get().getPrincipal())) {
//                    String creationResponse = adServ.createADCredentialsUpdateUserGetPass(user, userServ);
//                    if (creationResponse.equals("EXISTS")) {
//                        mailServ.prepareAndSendSkypeLinkExisting(user.get().getEmail(), mailName + " " + mailSurname, room.getName(), room.getUrl(),
//                                user.get().getPrincipal());
//                    } else {
//                        if (!creationResponse.equals("NOK")) {
//                            mailServ.prepareAndSendSkypeLink(user.get().getEmail(),
//                                    mailName + " " + mailSurname, room.getName(), room.getUrl(), user.get().getPrincipal(), creationResponse);
//                        } else {
//                            log.info("Error adding user to active directory");
//                            return "error";
//                        }
//                    }
//                } else {
//                    mailServ.prepareAndSendSkypeLinkExisting(user.get().getEmail(), mailName + " " + mailSurname, room.getName(), room.getUrl(),
//                            user.get().getPrincipal());
//                }
                ADHelpers.createUserAndSendEmail(user, mailServ, userServ, adServ, room.getName(), room.getUrl(), EmailTypes.Skype);
                try {
                    adServ.add2Group(user.get().getAdId(), "SkypeForBusiness", false);
                } catch (IOException ex) {
                    log.info("Error: ", ex);
                }
            }
            model.addAttribute("user", user.get());
        }
        return "skypeView";
    }

    @RequestMapping(value = {"team"})
    public String team(Principal principal, @CookieValue(value = "type", required = true) String typeCookie, Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        Optional<User> user = userServ.findByEid(principal.getName());
        //insert User into the Group “Teams” of the Azure AD  add2Grpup user ID is the new AD-USER-ID field in the db
        String teamId = typeCookie.split("-")[1];
        Optional<Teams> team = teamServ.findById(Long.parseLong(teamId));
        List<Teams> teams = teamServ.findAll();
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));

        String mailName = StringUtils.isEmpty(user.get().getEngName()) ? user.get().getCurrentGivenName() : user.get().getEngName();
        String mailSurname = StringUtils.isEmpty(user.get().getEngSurname()) ? user.get().getCurrentFamilyName() : user.get().getEngSurname();
        if (user.isPresent() && team.isPresent()) {
//            if (StringUtils.isEmpty(user.get().getPrincipal())) {
//                String creationResponse = adServ.createADCredentialsUpdateUserGetPass(user, userServ);
//
//                if (creationResponse.equals("EXISTS")) {
//                    mailServ.prepareAndSendTeamMessageExisting(user.get().getEmail(), mailName + " " + mailSurname, team.get().getName(), user.get().getPrincipal());
//                } else {
//                    if (!creationResponse.equals("NOK")) {
//                        mailServ.prepareAndSendTeamMessage(user.get().getEmail(), mailName + " " + mailSurname,
//                                team.get().getName(), user.get().getPrincipal(), creationResponse);
//                    } else {
//                        log.info("Error adding user to active directory");
//                        return "error";
//                    }
//                }
//            } else {
//                mailServ.prepareAndSendTeamMessageExisting(user.get().getEmail(), mailName + " " + mailSurname,
//                        team.get().getName(), user.get().getPrincipal());
//            }
            ADHelpers.createUserAndSendEmail(user, mailServ, userServ, adServ, team.get().getName(), team.get().getUrl(), EmailTypes.Team);

            try {
                adServ.add2Group(user.get().getAdId(), team.get().getName(), false);
            } catch (IOException ex) {
                log.info("Error: ", ex);
            }
//            model.addAttribute("TeamURL", propServ.getPropByName("TEAM_URL"));
            if (team.isPresent()) {
                model.addAttribute("TeamName", team.get().getName());
                model.addAttribute("TeamURL", team.get().getUrl());
                model.addAttribute("UserName", user.get().getPrincipal());
                model.addAttribute("user", user.get());
            }
            model.addAttribute("teams", teams);

            return "teamView";
        }
        return "error";
    }

    // //User Is inserted into the Group “UAegean-HPClass” of the Azure AD 
    @RequestMapping(value = {"roomaccess"})
    public String physical(Model model,
            Principal principal,
            @CookieValue(value = "type", required = true) String typeCookie,
            @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        Optional<User> user = userServ.findByEid(principal.getName());
        String roomId = typeCookie.split("-")[1];
        Optional<ClassRoom> room = classServ.getRoomById(roomId);

        if (room.isPresent()
                && !(room.get().getState().equals(RoomStatesEnum.INACTIVE.state())
                || room.get().getState().equals(RoomStatesEnum.RESTRICTED.state()))
                && user.isPresent()
                && !user.get().getRole().getName().equals(RolesEnum.BLACKLISTED.role())) {

            ActiveCode ac = new ActiveCode();
            ac.setContent(UtilGenerators.generateRandomPIN(4));
            ac.setGrantedAt(LocalDateTime.now());
            ActiveCodePK key = new ActiveCodePK(user.get(), room.get());
            ac.setId(key);
            activeServ.save(ac);
            model.addAttribute("pin", ac.getContent());
            model.addAttribute("room", room.get());
            model.addAttribute("user", user.get());
            String qrImgPath;
            try {
                qrImgPath = QRGenerator.generateQR(roomId, user.get().getEid(), ac.getContent());
                model.addAttribute("qrPath", qrImgPath);
                //add user to AD “UAegean-HPClass”
                adServ.add2Group(user.get().getAdId(), "UAegean-HPClass", false);
            } catch (IOException ex) {
                log.info("Error ", ex);
            }
        } else {
            if (!user.get().getRole().getName().equals(RolesEnum.BLACKLISTED.role())) {
                model.addAttribute("error", "Room is not available at this time. Please contact the classroom administrator");
            }
        }

        ADHelpers.createUserAndSendEmail(user, mailServ, userServ, adServ, null, null, EmailTypes.Physical);

        return "physicalView";
    }

    /**
     * Adds a new user to the database and to the Active Directory by making an
     * appropriate API call ***TODO desired role!!!!! createUser AD API ***ROLE
     * not specified in AD so not valid so ignore the part about user role here
     * in the API call***
     */
    @RequestMapping(value = "createUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String createUser(@ModelAttribute("user") FormUser user, Model model, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie) {
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        String userName;
        if (user.getEngName() != null && user.getEngSurname() != null) {
            userName = user.getEngName() + "." + user.getEngSurname();
            BaseResponse resp = userServ.saveOrUpdateUser(user.getEid(), user.getCurrentGivenName(), user.getCurrentFamilyName(),
                    GenderEnum.UNSPECIFIED.gender(), user.getDateOfBirth(), user.getEmail(),
                    user.getMobile(), user.getAffiliation(), user.getCountry(), null, null, user.getEngName(), user.getEngSurname());
            if (resp.getStatus().equals("OK")) {
//                mailServ.prepareAndSendAccountCreated(user.getEmail(), "Smart Class Account Details", userName);
                Optional<User> theUser = Optional.of(UserWrappers.wrapFormUserToDBUser(user, roleServ, genServ));
                ADHelpers.createUserAndSendEmail(theUser, mailServ, userServ, adServ, null, null, EmailTypes.AccountCreation);
                return "updateSuccessViewRegister";
            }
        } else {
            model.addAttribute("error", "no english user name found!! Cannot create user");
        }
        return "error";
    }

    /**
     * TODO updates the attributes of the user and makes the corresponding API
     * call to add the user to the active directory
     */
    @RequestMapping(value = "updateUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String updateUser(@ModelAttribute("user") FormUser user, @CookieValue(value = TOKEN_NAME, required = false) String jwtCookie, Model model) {
        Optional<User> oldUser = userServ.findByEid(user.getEid());
        String gender = GenderEnum.UNSPECIFIED.gender();
        model.addAttribute("loggedIn", !StringUtils.isEmpty(jwtCookie));
        if (oldUser.isPresent()) {
            gender = oldUser.get().getGender().getName();
            BaseResponse resp = userServ.saveOrUpdateUser(user.getEid(), user.getCurrentGivenName(), user.getCurrentFamilyName(),
                    gender, user.getDateOfBirth(), user.getEmail(), user.getMobile(), user.getAffiliation(), user.getCountry(),
                    oldUser.get().getAdId(), oldUser.get().getPrincipal(), oldUser.get().getEngName(), oldUser.get().getEngSurname());
            if (resp.getStatus().equals("OK")) {
                return "updateSuccessView";
            }
        }
        return "error";
    }

    /**
     * private admin view. Can only be accessed by admin users and allows to a)
     * change user roles b) change classRoomStatus c) edit skype for business
     * rooms d) edit physical rooms
     *
     * @return
     */
    @RequestMapping(value = "admin", method = {RequestMethod.GET})
    public String admin(Model model) {
        List<SkypeRoom> skRooms = skypeRoomServ.getAllRooms();
        List<User> unIdentified = userServ.findAllUIdentified();
        List<ClassRoom> rooms = classServ.findAll();
        List<ClassRoomState> roomStates = classServ.getAllRoomStates();

        model.addAttribute("skypeRooms", skRooms);
        model.addAttribute("unidentifiedUsers", unIdentified);
        model.addAttribute("rooms", rooms);
        model.addAttribute("roomStates", roomStates);
        return "adminView";
    }

    @RequestMapping(value = "adminLogin", method = {RequestMethod.GET})
    public String adminLoginView(Model model) {
        return "adminLoginView";
    }

    @RequestMapping(value = "admin/edit/{urId}", method = {RequestMethod.GET})
    public String adminEditUserView(Model model, @PathVariable("urId") String id) {
        Optional<User> user = userServ.findById(Long.parseLong(id));
        List<Role> roles = roleServ.findAllRoles();
        if (user.isPresent()) {
            FormUser fuser = UserWrappers.wrapDBUsertoFormUser(user.get());
            model.addAttribute("user", fuser);
            model.addAttribute("role", user.get().getRole().getName());
            model.addAttribute("roles", roles);
        } else {
            model.addAttribute("error", "user not found!");
        }
        return "adminEditUserView";

    }

    @RequestMapping(value = "admin/addSkypeRoom", method = {RequestMethod.GET})
    public String adminAddSkypeRoom(Model model) {

        return "addSkypeRoomView";

    }

}
