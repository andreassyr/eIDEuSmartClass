/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nikos
 */
public interface RoleService {
    
    public Optional<Role> getRoleByName(String roleName);
    public boolean updateUserRole(String userEid, String roleName);
    public List<Role> findAllRoles();

}
