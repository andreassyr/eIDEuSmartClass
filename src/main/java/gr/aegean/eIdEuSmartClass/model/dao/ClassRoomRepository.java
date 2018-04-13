/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoom;
import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoomState;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Dante
 */
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {

    @Override
    List<ClassRoom> findAll();

    Optional<ClassRoom> findByName(String name);

    List<ClassRoom> findByState(ClassRoomState state);
    
   
}
