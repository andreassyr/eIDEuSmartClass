/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import java.util.Optional;

/**
 *
 * @author nikos
 */
public interface GenderService {
    
    /**
     * if name is null or empty then unspecified gender is returned
     * @param genderName
     * @return 
     */
    public Optional<Gender> getGenderByName(String genderName);
    
}
