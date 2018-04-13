/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dao.RoleRepository;
import gr.aegean.eIdEuSmartClass.model.dao.UserRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.Role;
import gr.aegean.eIdEuSmartClass.model.dmo.User;
import gr.aegean.eIdEuSmartClass.model.service.RoleService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nikos
 */
@Service
public class RoleServiceImpl implements RoleService {

    
    @Autowired
    RoleRepository roleRepo;
    
    @Autowired
    UserRepository userRepo;
    
    @Override
    @Transactional
    public Optional<Role> getRoleByName(String roleName) {
        return roleRepo.findFirstByName(roleName);
    }

    @Override
    public boolean updateUserRole(String userEid, String roleName) {
        Optional<User> user = userRepo.findFirstByEIDASId(userEid);
        Optional<Role> role = roleRepo.findFirstByName(roleName);
        if(user.isPresent() && role.isPresent()){
            user.get().setRole(role.get());
            userRepo.save(user.get());
            return true;
        }
        return false;
    }
    
}
