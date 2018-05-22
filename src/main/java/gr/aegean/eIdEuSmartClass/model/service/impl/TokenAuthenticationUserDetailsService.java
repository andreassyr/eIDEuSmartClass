/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.GenderService;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import gr.aegean.eIdEuSmartClass.model.service.UserService;
import gr.aegean.eIdEuSmartClass.utils.pojo.FormUser;
import gr.aegean.eIdEuSmartClass.utils.wrappers.UserWrappers;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

    private UserService userService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TokenAuthenticationUserDetailsService.class);

    @Autowired
    private GenderService genServ;

    @Autowired
    private RoleService roleServ;

    @Autowired
    public TokenAuthenticationUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authentication) throws UsernameNotFoundException {
        String token = (String) authentication.getPrincipal();
        try {
            //            return new TokenUserDetails(token.getSubject(), token.getClaim("usr").asString(),
//                    (String) authentication.getCredentials(), token.getToken(), true, token
//                    .getClaim("role")
//                    .asList(String.class)
//                    .stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList()));

            FormUser formUser = UserWrappers.wrapDecodedJwtEidasUser(token);
            Optional<User> user = this.userService.findByEid(formUser.getEid());

            if (!user.isPresent()) {
                user = Optional.of(UserWrappers.wrapFormUserToDBUser(formUser, roleServ, genServ));
            }

            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.get().getRole().getName());

            PreAuthenticatedAuthenticationToken withAuthorities
                    = new PreAuthenticatedAuthenticationToken(token, token, authorities);

            return UserWrappers.wrapEidasToTokenUser(formUser, token, withAuthorities);
        } catch (IOException ex) {
            log.info("ERROR" + ex.getMessage());
            throw new UsernameNotFoundException("Could not wrap token", ex);
        }
    }
}
