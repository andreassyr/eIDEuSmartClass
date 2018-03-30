/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.service.TokenService;
import gr.aegean.eIdEuSmartClass.utils.pojo.TokenUserDetails;
import gr.aegean.eIdEuSmartClass.utils.wrappers.UserWrappers;
import io.jsonwebtoken.InvalidClaimException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class TokenAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private TokenService tokenService;

    @Autowired
    public TokenAuthenticationUserDetailsService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authentication) throws UsernameNotFoundException {
        if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof String && authentication.getCredentials() instanceof String) {
            String token;
            try {
                token = tokenService.decode((String) authentication.getPrincipal());
            } catch (InvalidClaimException ex) {
                throw new UsernameNotFoundException("Token has been expired", ex);
            } catch (UnsupportedEncodingException ex) {
                throw new UsernameNotFoundException("Wrong Encoding in token", ex);
            }

            try {
                //            return new TokenUserDetails(token.getSubject(), token.getClaim("usr").asString(),
//                    (String) authentication.getCredentials(), token.getToken(), true, token
//                    .getClaim("role")
//                    .asList(String.class)
//                    .stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList()));
                return UserWrappers.wrapEidasToTokenUser(UserWrappers.wrapDecodedJwtEidasUser(token),token,authentication);
            } catch (IOException ex) {
                throw new UsernameNotFoundException("Could not wrap token", ex);
            }
        }
        throw new UsernameNotFoundException("Could not retrieve user details for '" + authentication.getPrincipal() + "'");

    }
}
