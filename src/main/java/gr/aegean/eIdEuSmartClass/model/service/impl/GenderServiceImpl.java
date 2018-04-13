/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dao.GenderRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import gr.aegean.eIdEuSmartClass.model.service.GenderService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

/**
 *
 * @author nikos
 */
@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    GenderRepository genRepo;

    @Override
    public Optional<Gender> getGenderByName(String genderName) {
        if (StringUtils.isEmpty(genderName)) {
            return genRepo.findFirstByName(genderName);
        }else{
            return genRepo.findFirstByName("Unspecified");
        }
    }

}
