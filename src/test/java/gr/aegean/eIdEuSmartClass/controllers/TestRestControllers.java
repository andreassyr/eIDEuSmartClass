/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.controllers;

import gr.aegean.eIdEuSmartClass.EIdEuSmartClassApplication;
import gr.aegean.eIdEuSmartClass.controllers.TestRestControllers.mockConfig;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.security.TokenAuthenticationFilter;
import gr.aegean.eIdEuSmartClass.security.WebSecurityConfig;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.wrappers.DateWrappers;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.servlet.http.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author nikos
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {EIdEuSmartClassApplication.class, TokenAuthenticationFilter.class, WebSecurityConfig.class, mockConfig.class})
public class TestRestControllers {

    @Autowired
    private RestControllers restControllers;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ClassRoomService roomServ;

    @Autowired
    private ActiveCodeService activeServ;

    private MockMvc mockMvc;

    @Configuration
    static class mockConfig {

        @Bean
        @Primary
        public RoleService roleServ() {
            return Mockito.mock(RoleService.class);
        }

        @Bean
        @Primary
        public UserService useServ() {
            return Mockito.mock(UserService.class);
        }

        @Bean
        @Primary
        public ClassRoomService roomServ() {
            return Mockito.mock(ClassRoomService.class);
        }

        @Bean
        @Primary
        public ActiveCodeService activeServ() {
            return Mockito.mock(ActiveCodeService.class);
        }

    }

    @Autowired
    private RoleService roleServ;

    @Autowired
    private UserService userServ;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
        Role adminRole = new Role(RolesEnum.ADMIN.role());
        Gender testGen = new Gender("MALE");
        User adminUser = new User(adminRole, "adminId", "adminName",
                "adminSurname", "adminEmail", "adminMobile", "adminAffiliation", "admionCountry",
                testGen, DateWrappers.parseEidasDate("1983-10-05"), DateWrappers.getNowTimeStamp());

//        Mockito.when(roleServ.getRoleByName(any(String.class))).thenReturn(adminRole);
        Mockito.when(userServ.findByEid("GR/GR/ERMIS-11076669")).thenReturn(adminUser);
//
    }

    @Test
    public void testUpdateClassHasRole() throws Exception {
        Cookie c = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaXJzdE5hbWVcIjpcIs6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTXCIsXCJlaWRcIjpcIkdSL0dSL0VSTUlTLTExMDc2NjY5XCIsXCJmYW1pbHlOYW1lXCI6XCLOoM6VzqTOoc6fzqUsIFBFVFJPVVwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiR1IvR1IvRVJNSVMtMTEwNzY2NjlcIixcImRhdGVPZkJpcnRoXCI6XCIxOTgwLTAxLTAxXCJ9In0.QjyOqUi8kzU7Srn1FgekuQyn-REWwOWLKKmQAz92O48");
        Cookie c2 = new Cookie("type", "skype");
        Cookie[] cookies = new Cookie[2];
        cookies[0] = c;
        cookies[1] = c2;
        mockMvc.perform(get("/updateclass").param("roomName", "room1").param("roomStatus", "testStatus")
                .cookie(cookies))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateClassHasRoleNoJWT() throws Exception {
        Cookie c = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJmaXJzdE5hbWVcIjpcIs6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTXCIsXCJlaWRcIjpcIkdSL0dSL0VSTUlTLTExMDc2NjY5XCIsXCJmYW1pbHlOYW1lXCI6XCLOoM6VzqTOoc6fzqUsIFBFVFJPVVwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiR1IvR1IvRVJNSVMtMTEwNzY2NjlcIixcImRhdGVPZkJpcnRoXCI6XCIxOTgwLTAxLTAxXCJ9In0.QjyOqUi8kzU7Srn1FgekuQyn-REWwOWLKKmQAz92O48");
        Cookie c2 = new Cookie("type", "skype");
        Cookie[] cookies = new Cookie[2];
        cookies[0] = c;
        cookies[1] = c2;
        mockMvc.perform(get("/updateclass").param("roomName", "room1").param("roomStatus", "testStatus"))
                .andExpect(status().is(302));
    }

    @Test
    public void testValidateCode() throws Exception {
        mockMvc.perform(get("/validateCode").param("roomId", "room1").param("qrCode", "123"))
                .andExpect(status().isOk());
    }

}
