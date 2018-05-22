/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.EIdEuSmartClassApplication;
import gr.aegean.eIdEuSmartClass.controllers.TestViewControllers.configView;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.security.TokenAuthenticationFilter;
import gr.aegean.eIdEuSmartClass.security.WebSecurityConfig;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.wrappers.DateWrappers;
import java.util.Optional;
import javax.servlet.http.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author nikos
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {EIdEuSmartClassApplication.class, TokenAuthenticationFilter.class,
    WebSecurityConfig.class, configView.class})
public class TestViewControllers {

    @Autowired
    private ViewControllers viewControllers;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private UserService userServ;

    private MockMvc mockMvc;

    @Configuration
    static class configView {

        @Bean
        @Primary
        public UserService useServ() {
            return Mockito.mock(UserService.class);
        }
    }

    private final static String adminToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaXJzdE5hbWVcIjpcIs6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTXCIsXCJlaWRcIjpcIkdSL0dSL0VSTUlTLTExMDc2NjY5XCIsXCJmYW1pbHlOYW1lXCI6XCLOoM6VzqTOoc6fzqUsIFBFVFJPVVwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiR1IvR1IvRVJNSVMtMTEwNzY2NjlcIixcImRhdGVPZkJpcnRoXCI6XCIxOTgwLTAxLTAxXCJ9In0.QjyOqUi8kzU7Srn1FgekuQyn-REWwOWLKKmQAz92O48";
    private final static String guestToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJlaWRcIjpcIkdSL0dSL0VSTUlTLTU4MzMzOTQ3XCIsXCJwZXJzb25JZGVudGlmaWVyXCI6XCJHUi9HUi9FUk1JUy01ODMzMzk0N1wiLFwiZGF0ZU9mQmlydGhcIjpcIjE5ODAtMDEtMDFcIixcImN1cnJlbnRGYW1pbHlOYW1lXCI6XCLOoM6RzpvOmc6fzprOqc6jzqTOkVwiLFwiY3VycmVudEdpdmVuTmFtZVwiOlwizqfOoc6ZzqPOpM6Zzp3OkVwifSJ9.yN6fMvMZL-58Jnr5uFf3giO-g7oRfz4wiJnVxnF6Ajw";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
        Role adminRole = new Role(RolesEnum.ADMIN.role());
        Gender testGen = new Gender("MALE");
        User adminUser = new User(adminRole, "adminId", "adminName",
                "adminSurname", "adminEmail", "adminMobile", "adminAffiliation", "admionCountry",
                testGen, DateWrappers.parseEidasDate("1983-10-05"), DateWrappers.getNowTimeStamp(),"engName","engSurname");

        User guestUser = new User(new Role(RolesEnum.UNIDENTIFIED.role()), "guestId", "adminName",
                "adminSurname", "adminEmail", "adminMobile", "adminAffiliation", "admionCountry",
                testGen, DateWrappers.parseEidasDate("1983-10-05"), DateWrappers.getNowTimeStamp(),"engName","engSurname");

        Mockito.when(userServ.findByEid("GR/GR/ERMIS-11076669")).thenReturn(Optional.of(adminUser));
        //GR/GR/ERMIS-58333947
        Mockito.when(userServ.findByEid("GR/GR/ERMIS-58333947")).thenReturn(Optional.of(guestUser));
    }

    @Test
    public void testLoggedInAdmin() throws Exception {
        Cookie c = new Cookie("access_token", adminToken);
        Cookie c2 = new Cookie("type", "skype");
        Cookie[] cookies = new Cookie[2];
        cookies[0] = c;
        cookies[1] = c2;
        mockMvc.perform(get("/eIDASSuccess")
                .cookie(cookies))
                .andExpect(redirectedUrl("/skype"));
    }

    //
    @Test
    public void testLoggedValidUnregisterd() throws Exception {
        Cookie c = new Cookie("access_token", guestToken);
        Cookie c2 = new Cookie("type", "team");
        Cookie[] cookies = new Cookie[2];
        cookies[0] = c;
        cookies[1] = c2;
        mockMvc.perform(get("/eIDASSuccess")
                .cookie(cookies))
                .andExpect(redirectedUrl("/pending"));
    }

    @Test
    public void testLoggedNOTValidJWTTeam() throws Exception {
        Cookie c = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaXJzdE5hbWVcIjpcIs6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTXCIsXCJlaWRcIjpcIkdSL0dSL0VSTUlTLTExMDc2NjY5XCIsXCJmYW1pbHlOYW1lXCI6XCLOoM6VzqTOoc6fzqUsIFBFVFJPVVwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiR1IvR1IvRVJNSVMtMTEwNzY2NjlcIixcImRhdGVPZkJpcnRoXCI6XCIxOTgwLTAxLTAxXCJ9In0.QjyOqUi8kzU7Srn1FgekuQyn-REWwOWLKKmQAz92O41");
        Cookie c2 = new Cookie("type", "team");
        Cookie[] cookies = new Cookie[2];
        cookies[0] = c;
        cookies[1] = c2;
        mockMvc.perform(get("/eIDASSuccess")
                .cookie(cookies))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/error"));

    }

    @Test
    public void testAdminvViewValidJWTnotAdmin() throws Exception {
        Cookie c = new Cookie("access_token", guestToken);
        Cookie[] cookies = new Cookie[2];
        cookies[0] = c;
        mockMvc.perform(get("/admin")
                .cookie(c))
                //                                .andExpect(redirectedUrl("/adminLogin"));
                .andExpect(status().is(403));
    }

    @Test
    public void testAdminInvalidJWTAdmin() throws Exception {
        Cookie c = new Cookie("access_token", "invalide");
        Cookie[] cookies = new Cookie[2];
        cookies[0] = c;
        mockMvc.perform(get("/admin")
                .cookie(c))
                .andExpect(status().is(403));
    }

    @Test
    public void testAdminvViewnoJWT() throws Exception {

        mockMvc.perform(get("/admin"))
                .andExpect(redirectedUrl("http://localhost/adminLogin"));
    }

}
