/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import gr.aegean.eIdEuSmartClass.model.dmo.ClassRoomState;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Dante
 */
public interface ClassRoomStateRepository extends JpaRepository<ClassRoomState, Long>
{
    Optional<ClassRoomState> findByName(String name);
}
