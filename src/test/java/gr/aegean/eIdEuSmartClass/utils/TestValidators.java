/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils;

import gr.aegean.eIdEuSmartClass.EIdEuSmartClassApplication;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCodePK;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import gr.aegean.eIdEuSmartClass.security.TokenAuthenticationFilter;
import gr.aegean.eIdEuSmartClass.security.WebSecurityConfig;
import gr.aegean.eIdEuSmartClass.utils.TestValidators.mockConfig;
import gr.aegean.eIdEuSmartClass.utils.enums.GenderEnum;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import gr.aegean.eIdEuSmartClass.utils.validators.ValidateRoomCode;
import gr.aegean.eIdEuSmartClass.utils.wrappers.DateWrappers;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author nikos
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {EIdEuSmartClassApplication.class, TokenAuthenticationFilter.class, WebSecurityConfig.class, mockConfig.class})
public class TestValidators {

    @Autowired
    private ActiveCodeService activeServ;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Configuration
    static class mockConfig {

        @Bean
        @Primary
        public ActiveCodeService activeServ() {
            return Mockito.mock(ActiveCodeService.class);
        }
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();

        LocalDateTime todayAt558 = LocalDate.now().atTime(5, 58);
        List<ActiveCode> codes = new ArrayList();
        ActiveCode testCode = new ActiveCode();
        testCode.setContent("1234");
        testCode.setGrantedAt(todayAt558);
        codes.add(testCode);

        List<ActiveCode> badCodes = new ArrayList();
        ActiveCode badTestCode = new ActiveCode();
        badTestCode.setContent("2345");
        badTestCode.setGrantedAt(DateWrappers.parseDateTime("2018-04-04 22:01"));
        badCodes.add(badTestCode);
        Role badRole = new Role(RolesEnum.VISITOR.role());
        Gender badGender = new Gender(GenderEnum.UNSPECIFIED.gender());
        User badUser = new User(badRole, "e", "n", "s", "e", "m", "a", "c", badGender, LocalDate.now(), null,"engName","engSurname");
        ActiveCodePK badKey = new ActiveCodePK(badUser, null);
        badTestCode.setId(badKey);

        LocalDateTime todayAt458 = LocalDate.now().atTime(5, 58);
        List<ActiveCode> adminCodes = new ArrayList();
        ActiveCode adminCode = new ActiveCode();
        adminCode.setContent("4567");
        adminCode.setGrantedAt(todayAt458);
        adminCodes.add(adminCode);
        Role adminRole = new Role(RolesEnum.ADMIN.role());
        Gender gender = new Gender(GenderEnum.UNSPECIFIED.gender());
        User user = new User(adminRole, "e", "n", "s", "e", "m", "a", "c", gender, LocalDate.now(), null,"engName","engSurname");
        ActiveCodePK key = new ActiveCodePK(user, null);
        adminCode.setId(key);

        List<ActiveCode> superAdminCodes = new ArrayList();
        ActiveCode superAdminCode = new ActiveCode();
        superAdminCode.setContent("1111");
        superAdminCode.setGrantedAt(todayAt458);
        superAdminCodes.add(superAdminCode);
        Role supeAadminRole = new Role(RolesEnum.SUPERADMIN.role());
        User suser = new User(supeAadminRole, "e", "n", "s", "e", "m", "a", "c", gender, LocalDate.now(), null,"engName","engSurname");
        ActiveCodePK skey = new ActiveCodePK(suser, null);
        superAdminCode.setId(skey);

        Mockito.when(activeServ.getCodesByContent("1234")).thenReturn(adminCodes);
        Mockito.when(activeServ.getCodesByContent("2345")).thenReturn(badCodes);
        Mockito.when(activeServ.getCodesByContent("4567")).thenReturn(adminCodes);
        Mockito.when(activeServ.getCodesByContent("1111")).thenReturn(superAdminCodes);
    }

    @Test
    public void testValidateCode() {
        LocalDateTime todayAt6 = LocalDate.now().atTime(6, 0);
        assertEquals(ValidateRoomCode.validateCode("1234", activeServ, todayAt6), true);
    }

    @Test
    public void testInValidateCode() {
        assertEquals(ValidateRoomCode.validateCode("2345", activeServ, LocalDateTime.now()), false);
    }

    @Test
    public void testAdminCode() {
        LocalDateTime todayAt21 = LocalDate.now().atTime(21, 0);
        assertEquals(ValidateRoomCode.validateCode("4567", activeServ, todayAt21), true);
    }

    @Test
    public void testAdminCodeAfter22() {
        LocalDateTime todayAt2201 = LocalDate.now().atTime(22, 1);
        assertEquals(ValidateRoomCode.validateCode("4567", activeServ, todayAt2201), false);
    }

    @Test
    public void testSuperAdminCodeAfter22() {
        LocalDateTime todayAt2201 = LocalDate.now().atTime(23, 1);
        assertEquals(ValidateRoomCode.validateCode("1111", activeServ, todayAt2201), true);
    }

}
