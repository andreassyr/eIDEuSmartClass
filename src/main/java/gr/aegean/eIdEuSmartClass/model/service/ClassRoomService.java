/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoomState;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nikos
 */
public interface ClassRoomService {
    
    public List<String> getValidCodeByName(String roomName);
    public List<ClassRoom> findAll();
    
    public boolean setRoomStatusByStateName(String stateName, String name); 
   
    public Optional<ClassRoomState> getRoomStatus(String roomName);
    
    public Optional<ClassRoom> getRoomById(String id);
    
    public List<ClassRoomState> getAllRoomStates();
}
