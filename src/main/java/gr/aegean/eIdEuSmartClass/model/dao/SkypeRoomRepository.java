/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import gr.aegean.eIdEuSmartClass.model.dmo.SkypeRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nikos
 */
public interface SkypeRoomRepository extends JpaRepository<SkypeRoom, Long> {

    @Override
    public List<SkypeRoom> findAll();
}
