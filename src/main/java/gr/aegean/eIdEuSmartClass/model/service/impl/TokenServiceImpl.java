/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.service.ConfigPropertiesServices;
import gr.aegean.eIdEuSmartClass.model.service.TokenService;
import io.jsonwebtoken.Jwts;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private ConfigPropertiesServices propServ;

    @Override
    public String decode(String token) throws UnsupportedEncodingException {
        return Jwts.parser().setSigningKey(propServ.getPropByName("KEY").getBytes("UTF-8"))
                .parseClaimsJws(token).getBody().getSubject();
    }

}
