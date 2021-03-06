/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import gr.aegean.eIdEuSmartClass.model.dmo.Gender;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nikos
 */
public interface GenderRepository extends JpaRepository<Gender, Long> {

    @Override
    List<Gender> findAll();

    Optional<Gender> findByName(String name);
    public Optional<Gender> findFirstByName(String name);
}
