/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.security;

import gr.aegean.eIdEuSmartClass.model.service.TokenService;
import gr.aegean.eIdEuSmartClass.model.service.impl.TokenAuthenticationUserDetailsService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

/**
 *
 * @author nikos
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private TokenAuthenticationUserDetailsService tokenAuthUserDetailsService;
    private TokenService tokenService;

    public TokenAuthenticationFilter(TokenAuthenticationUserDetailsService userDetailsService, TokenService tokenService) {
        this.tokenAuthUserDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        if (request.getCookies() != null) {

            Optional<String> token = Arrays.asList(request.getCookies()).stream().filter(cookie -> {
                return cookie.getName().equals("access_token");
            }).map(cookie -> {
                return cookie.getValue();
            }).findFirst();

            if (token.isPresent() && !StringUtils.isEmpty(token.get())) {

                String tokenDecoded = this.tokenService.decode(token.get());

                if (!tokenDecoded.equals("{}") && !StringUtils.isEmpty(tokenDecoded)) {
                    PreAuthenticatedAuthenticationToken authtoken = new PreAuthenticatedAuthenticationToken(tokenDecoded, tokenDecoded);
                    UserDetails userDetails = this.tokenAuthUserDetailsService.loadUserDetails(authtoken);
                    SecurityContextHolder.getContext().setAuthentication(new TokenBasedAuthentication(userDetails));
                } else {
                    SecurityContextHolder.getContext().setAuthentication(new AnonAuthentication());
                }

            } 
//            else {
//                // if JWT is empty
//                //prevent show login form again... but instead we provide an unAunticated user here with no authorities
//                SecurityContextHolder.getContext().setAuthentication(new AnonAuthentication());
//            }

        }
        chain.doFilter(request, response);
    }
}
