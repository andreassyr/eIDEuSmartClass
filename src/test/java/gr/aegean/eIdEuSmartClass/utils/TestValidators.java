/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils;

import gr.aegean.eIdEuSmartClass.EIdEuSmartClassApplication;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.security.TokenAuthenticationFilter;
import gr.aegean.eIdEuSmartClass.security.WebSecurityConfig;
import gr.aegean.eIdEuSmartClass.utils.TestValidators.mockConfig;
import gr.aegean.eIdEuSmartClass.utils.validators.ValidateRoomCode;
import gr.aegean.eIdEuSmartClass.utils.wrappers.DateWrappers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
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
        List<ActiveCode> codes = new ArrayList();
        ActiveCode testCode = new ActiveCode();
        testCode.setContent("1234");
        testCode.setGrantedAt(LocalDateTime.now());
        codes.add(testCode);
        
        List<ActiveCode> badCodes = new ArrayList();
        ActiveCode badTestCode = new ActiveCode();
        badTestCode.setContent("2345");
        badTestCode.setGrantedAt(DateWrappers.parseDateTime("2018-04-04 22:01"));
        badCodes.add(badTestCode);
        
        
        Mockito.when(activeServ.getCodesByContent("1234")).thenReturn(codes);
        Mockito.when(activeServ.getCodesByContent("2345")).thenReturn(badCodes);
    }

    @Test
    public void testValidateCode() {
        assertEquals(ValidateRoomCode.isValidActiveCode("1234", activeServ),true);
    }
    
    @Test
    public void testInValidateCode() {
        assertEquals(ValidateRoomCode.isValidActiveCode("2345", activeServ),false);
    }
    

}
