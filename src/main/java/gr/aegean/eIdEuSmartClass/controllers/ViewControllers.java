/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.model.service.ConfigPropertiesServices;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    

    public final static String TOKEN_NAME = "access_token";
    private final static Logger LOG = LoggerFactory.getLogger(ViewControllers.class);

    @RequestMapping(value = {"landing", "/", ""})
    public String landing(@CookieValue(value = TOKEN_NAME, required = false) String jwtCookie,
            @CookieValue(value = "flow", required = false) String flowCookie,
            HttpServletRequest req) throws UnsupportedEncodingException, IOException, IOException {

        if (jwtCookie == null) {
            return "redirect:/login";
        } else {
            if (flowCookie != null) {
                if (flowCookie.equals("login")) {
                    return "loggedIn";
                } else {
                    return "register";
                }
            }
            return "redirect:/login";
        }
    }

    @RequestMapping(value = {"login"})
    public String loginOrRegister() {
        return "login";
    }

    @RequestMapping(value = {"loggedIn"})
    public String loggedIn() {
        return "loggedIn";
    }

}
