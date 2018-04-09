/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nikos
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Override
    List<Role> findAll();
    Optional<Role> findByName(String name);
    public Optional<Role> findFirstByName(String name);
}
