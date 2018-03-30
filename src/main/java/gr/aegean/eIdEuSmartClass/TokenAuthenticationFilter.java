/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass;

import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 *
 * @author nikos
 */
public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        logger.info("Retrieving principal from token");
//        return request.getHeader("X-Token");
        if (request.getCookies() != null) {
            Optional<String> token = Arrays.asList(request.getCookies()).stream().filter(cookie -> {
                return cookie.getName().equals("access_token");
            }).map(cookie -> {
                return cookie.getValue();
            }).findFirst();

            if (token.isPresent()) {
                return token.get();
            }
        }
        return null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
//       logger.info("Retrieving principal from token");
//        return request.getHeader("X-Token");
        if (request.getCookies() != null) {
            Optional<String> token = Arrays.asList(request.getCookies()).stream().filter(cookie -> {
                return cookie.getName().equals("access_token");
            }).map(cookie -> {
                return cookie.getValue();
            }).findFirst();

            if (token.isPresent()) {
                return token.get();
            }
        }
        return null;
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
