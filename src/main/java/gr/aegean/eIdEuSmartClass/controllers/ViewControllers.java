/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nikos
 */
@Controller
public class ViewControllers {

    public final static String TOKEN_NAME = "access_token";

    @RequestMapping(value = {"landing", "/", ""})
    public String landing(@CookieValue(value = TOKEN_NAME, required = false) String jwtCookie, 
            @CookieValue(value = "flow", required = false) String flowCookie) {
        if (jwtCookie == null) {
            return "redirect:/login";
        } else {
            if(flowCookie != null){
                if(flowCookie == "login"){
                    return "loggedIn";
                }else{
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

    
    
    
}
