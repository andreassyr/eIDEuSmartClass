/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import java.util.List;

/**
 *
 * @author nikos
 */
public interface ActiveCodeService {
    public  List<ActiveCode> getCodesByContent(String content);
    public  void save(ActiveCode ac);

}
