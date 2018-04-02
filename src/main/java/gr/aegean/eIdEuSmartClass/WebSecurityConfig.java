/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass;

import gr.aegean.eIdEuSmartClass.model.service.impl.TokenAuthenticationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

/**
 *
 * @author nikos
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenAuthenticationUserDetailsService service;

    
    @Autowired
    public WebSecurityConfig(TokenAuthenticationUserDetailsService service) {
        this.service = service;
    }

    
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/loggedIn")
//                .authorizeRequests()
                .antMatcher("/register")
                .authorizeRequests()
//                .mvcMatchers(HttpMethod.GET, "/login").anonymous() //anonymous()
                .mvcMatchers(HttpMethod.GET, "/landing").anonymous() //anonymous()
                .mvcMatchers(HttpMethod.GET, "/").anonymous() //anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/landing")
                .and()
                .addFilterBefore(authFilter(), RequestHeaderAuthenticationFilter.class)
                .authenticationProvider(preAuthProvider())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                ;
    }

    @Bean
    public TokenAuthenticationFilter authFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public AuthenticationProvider preAuthProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(service);
        return provider;
    }
    
    

}
