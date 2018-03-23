/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import gr.aegean.eIdEuSmartClass.model.dmo.RoomState;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nikos
 */
public interface RoomStatesRepository extends JpaRepository<RoomState, Long> {

    @Override
    public List<RoomState> findAll();

    @Query("from RoomState S where S.name = :name")
    public Optional<RoomState> findByName(@Param("name") String name);
}
