/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Dante
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    public List<User> findAll();

    @Query("from User U where U.eIDAS_id = :id")
    public User findFirstByEIDASId(@Param("id")String eIDASid);

}
