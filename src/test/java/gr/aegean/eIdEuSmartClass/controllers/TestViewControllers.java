/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.EIdEuSmartClassApplication;
import gr.aegean.eIdEuSmartClass.TokenAuthenticationFilter;
import gr.aegean.eIdEuSmartClass.WebSecurityConfig;
import javax.servlet.http.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 *
 * @author nikos
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {EIdEuSmartClassApplication.class, TokenAuthenticationFilter.class, WebSecurityConfig.class})
public class TestViewControllers {

    @Autowired
    private ViewControllers viewControllers;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
    }

//    @Test
//    public void testLoggedInNoJWT() throws Exception {
//        mockMvc.perform(get("/loggedIn"))
//                .andExpect(redirectedUrl("http://localhost/landing"));
//    }

    @Test
    public void testLoggedValidJWT() throws Exception {
        Cookie c = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaXJzdE5hbWVcIjpcIs6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTXCIsXCJlaWRcIjpcIkdSL0dSL0VSTUlTLTExMDc2NjY5XCIsXCJmYW1pbHlOYW1lXCI6XCLOoM6VzqTOoc6fzqUsIFBFVFJPVVwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiR1IvR1IvRVJNSVMtMTEwNzY2NjlcIixcImRhdGVPZkJpcnRoXCI6XCIxOTgwLTAxLTAxXCJ9In0.QjyOqUi8kzU7Srn1FgekuQyn-REWwOWLKKmQAz92O48");
         Cookie c2 = new Cookie("type", "skype");
        Cookie[] cookies = new Cookie[2];
        cookies[0]=c;
        cookies[1]=c2;
        mockMvc.perform(get("/loggedIn")
                .cookie(cookies))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testLoggedInValidJWT() throws Exception {
//        Cookie c = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaXJzdE5hbWVcIjpcIs6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTXCIsXCJlaWRcIjpcIkdSL0dSL0VSTUlTLTExMDc2NjY5XCIsXCJmYW1pbHlOYW1lXCI6XCLOoM6VzqTOoc6fzqUsIFBFVFJPVVwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiR1IvR1IvRVJNSVMtMTEwNzY2NjlcIixcImRhdGVPZkJpcnRoXCI6XCIxOTgwLTAxLTAxXCJ9In0.QjyOqUi8kzU7Srn1FgekuQyn-REWwOWLKKmQAz92O482");
//        mockMvc.perform(get("/loggedIn")
//                .cookie(c))
//                .andExpect(redirectedUrl("http://localhost/landing"));
//    }

//    @Test
//    public void testLoggedInJWTLandingLoginFlow() throws Exception {
//        Cookie c = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaXJzdE5hbWVcIjpcIs6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTXCIsXCJlaWRcIjpcIkdSL0dSL0VSTUlTLTExMDc2NjY5XCIsXCJmYW1pbHlOYW1lXCI6XCLOoM6VzqTOoc6fzqUsIFBFVFJPVVwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiR1IvR1IvRVJNSVMtMTEwNzY2NjlcIixcImRhdGVPZkJpcnRoXCI6XCIxOTgwLTAxLTAxXCJ9In0.QjyOqUi8kzU7Srn1FgekuQyn-REWwOWLKKmQAz92O48");
//        Cookie c2 = new Cookie("flow", "login");
//        Cookie[] cookies = new Cookie[2];
//        cookies[0] = c;
//        cookies[1] = c2;
//        mockMvc.perform(get("/")
//                .cookie(cookies))
//                .andExpect(redirectedUrl("/loggedIn"));
//    }
//
//    @Test
//    public void testLoggedInJWTLandingRegisterFlow() throws Exception {
//        Cookie c = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaXJzdE5hbWVcIjpcIs6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTXCIsXCJlaWRcIjpcIkdSL0dSL0VSTUlTLTExMDc2NjY5XCIsXCJmYW1pbHlOYW1lXCI6XCLOoM6VzqTOoc6fzqUsIFBFVFJPVVwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiR1IvR1IvRVJNSVMtMTEwNzY2NjlcIixcImRhdGVPZkJpcnRoXCI6XCIxOTgwLTAxLTAxXCJ9In0.QjyOqUi8kzU7Srn1FgekuQyn-REWwOWLKKmQAz92O48");
//        Cookie c2 = new Cookie("flow", "register");
//        Cookie[] cookies = new Cookie[2];
//        cookies[0] = c;
//        cookies[1] = c2;
//        mockMvc.perform(get("/")
//                .cookie(cookies))
//                .andExpect(redirectedUrl("/landingView"));
//    }

}
