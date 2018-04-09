/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dao.ActiveCodeRepository;
import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dao.ClassRoomStateRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoomState;
import gr.aegean.eIdEuSmartClass.model.service.ClassRoomService;
import java.util.List;
import java.util.Optional;
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
    private ClassRoomStateRepository statesRepo;
    
    @Autowired
    private ClassRoomRepository classRoomRepo;
    
    @Override
    @Transactional
    public List<String> getValidCodeByName(String name) {
        return activeCodeRepo.getContentFromClassRoom(name);
    }

    @Override
    @Transactional
    public List<ClassRoom> findAll() {
        return classRoomRepo.findAll();
    }

    @Override
    @Transactional
    public boolean setRoomStatusByStateName(String stateName, String roomName) {
        Optional<ClassRoomState> state = statesRepo.findByName(stateName);
        Optional<ClassRoom> room = classRoomRepo.findByName(roomName);
        if(state.isPresent() && room.isPresent()){
            room.get().setState(state.get());
            classRoomRepo.save(room.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Optional<ClassRoomState> getRoomStatus(String roomName) {
        Optional<ClassRoomState> result = Optional.empty();
        Optional<ClassRoom> room = classRoomRepo.findByName(roomName);
        if (room.isPresent()){
            result = Optional.of(room.get().getState());
        }
        return result;
    }

    @Override
    @Transactional
    public Optional<ClassRoom> getRoomById(String id) {
        Optional<ClassRoom> room =  classRoomRepo.findById(Long.parseLong(id));
        return (room.isPresent())?Optional.of(room.get()):Optional.empty();
    }

}
