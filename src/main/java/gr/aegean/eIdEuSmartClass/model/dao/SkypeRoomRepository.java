/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import gr.aegean.eIdEuSmartClass.model.dmo.SkypeRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nikos
 */
public interface SkypeRoomRepository extends JpaRepository<SkypeRoom, Long> {

    @Override
    public List<SkypeRoom> findAll();

    @Transactional
    @Modifying
    @Query("delete from SkypeRoom sr where sr.id= :id ")
    public void deleteRoom(@Param("id") Long id);
}
