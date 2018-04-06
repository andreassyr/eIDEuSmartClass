/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dao.ActiveCodeRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nikos
 */
@Service
public class ActiveCodeServiceImpl implements ActiveCodeService{

    @Autowired
    private ActiveCodeRepository activeRepo;
    
    @Override
    @Transactional
    public List<ActiveCode> getCodesByContent(String content) {
        return activeRepo.getCodeFromContent(content);
    }

    @Override
    @Transactional
    public void save(ActiveCode ac) {
        activeRepo.save(ac);
    }
    
}
