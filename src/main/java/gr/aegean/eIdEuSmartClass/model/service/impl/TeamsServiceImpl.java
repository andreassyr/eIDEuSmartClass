/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dao.TeamsRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.Teams;
import gr.aegean.eIdEuSmartClass.model.service.TeamsService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nikos
 */
@Service
public class TeamsServiceImpl implements TeamsService{

    @Autowired
    private TeamsRepository teamsRepo;
    
    @Override
    @Transactional
    public List<Teams> findAll() {
        return teamsRepo.findAll();
    }

    @Override
    public Optional<Teams> findById(Long id) {
        return teamsRepo.findById(id);
    }
    
}
