/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Dante
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    public List<User> findAll();

    @Query("from User U where U.eid = :id")
    public Optional<User> findFirstByEIDASId(@Param("id")String eIDASid);
    
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.lastLogin = :date where u.eid = :eId")
    public void updateLastLoginByeIDASID(@Param("eId")String eId, @Param("date") Timestamp date);
    
}
