/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.Teams;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nikos
 */
public interface TeamsService {
    
    public List<Teams> findAll();
    public Optional<Teams> findById(Long id);
    public Optional<Teams> findFirst();
}
