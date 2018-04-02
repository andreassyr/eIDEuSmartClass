/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dao.ActiveCodeRepository;
import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nikos
 */
@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Autowired
    private ActiveCodeRepository activeCodeRepo;

    
    @Autowired
    private ClassRoomRepository classRoomRepo;
    
    @Override
    @Transactional
    public String getValidCodeByName(String name) {
        return activeCodeRepo.getContentFromClassRoom(name);
    }

    @Override
    public List<ClassRoom> findAll() {
        return classRoomRepo.findAll();
    }

}
