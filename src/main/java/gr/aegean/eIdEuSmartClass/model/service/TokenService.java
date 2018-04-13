/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author nikos
 */
public interface TokenService {
    public String decode(String token) throws UnsupportedEncodingException;
}
