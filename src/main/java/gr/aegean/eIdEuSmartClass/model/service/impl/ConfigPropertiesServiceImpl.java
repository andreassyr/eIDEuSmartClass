/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.service.ConfigPropertiesServices;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class ConfigPropertiesServiceImpl implements ConfigPropertiesServices{

    @Override
    public String getPropByName(String name) {
        return System.getenv().get(name);
    }
    
}
