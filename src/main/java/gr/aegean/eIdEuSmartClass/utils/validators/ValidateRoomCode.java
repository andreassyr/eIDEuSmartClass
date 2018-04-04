/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.validators;

import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *
 * @author nikos
 */
public class ValidateRoomCode {

    public static boolean isValidActiveCode(String ac, ActiveCodeService activeService) {
        List<ActiveCode> codes = activeService.getCodesByContent(ac);
        if (codes.size() == 1) {
            ActiveCode matchingCode = codes.get(0);
            LocalDateTime codeTime = matchingCode.getGrantedAt();
            LocalDateTime present = LocalDateTime.now();
            long hours = codeTime.until(present, ChronoUnit.HOURS);
            if (codeTime.getHour() < 22) {
                return hours <= 4;
            }
        }
        return false;
    }

}
