/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.security;

import gr.aegean.eIdEuSmartClass.model.service.TokenService;
import gr.aegean.eIdEuSmartClass.model.service.impl.TokenAuthenticationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

/**
 *
 * @author nikos
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenAuthenticationUserDetailsService userDetailsService;
    private TokenService tokenService;

    @Autowired
    public WebSecurityConfig(TokenAuthenticationUserDetailsService userDetailsService, TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                //                .antMatcher("/loggedIn")
                //                .antMatchers("/register")
                //                .antMatchers("/requestCloseRoom")
                //                .antMatchers("/createUser")
                //                .authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/landing").permitAll()
                .and()
                .addFilterBefore(authFilter(), RequestHeaderAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }

    @Bean
    public TokenAuthenticationFilter authFilter() {
        return new TokenAuthenticationFilter(userDetailsService, tokenService);
    }
}
