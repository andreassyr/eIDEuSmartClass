/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.Role;

/**
 *
 * @author nikos
 */
public interface RoleService {
    
    public Role getRoleByName(String roleName);
    
}
