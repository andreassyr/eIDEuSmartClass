/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dao;

import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nikos
 */
public interface ActiveCodeRepository extends JpaRepository<ActiveCode, Long> {

    @Override
    List<ActiveCode> findAll();
    
    @Query("select ac.content from ActiveCode ac join ClassRoom cl on ac.id.classRoom = cl.id where cl.name = :name")
    public List<String> getContentFromClassRoom(@Param("name")String name);
    
}
