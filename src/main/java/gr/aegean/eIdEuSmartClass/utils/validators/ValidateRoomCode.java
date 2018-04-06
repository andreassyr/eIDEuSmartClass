/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.validators;

import gr.aegean.eIdEuSmartClass.model.dmo.ActiveCode;
import gr.aegean.eIdEuSmartClass.model.service.ActiveCodeService;
import gr.aegean.eIdEuSmartClass.utils.enums.RolesEnum;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *
 * @author nikos
 */
public class ValidateRoomCode {

    /**
     * validates the code and if the conditions are met the code is made active
     *
     * @param ac
     * @param activeService
     * @return
     */
    public static boolean validateCode(String ac, ActiveCodeService activeService, LocalDateTime present) {
        List<ActiveCode> codes = activeService.getCodesByContent(ac);
        if (codes.size() == 1) {
            ActiveCode matchingCode = codes.get(0);
            LocalDateTime codeTime = matchingCode.getGrantedAt();
//            LocalDateTime present = LocalDateTime.now();
            long hours = codeTime.until(present, ChronoUnit.HOURS);
            
            if (present.getHour() < 22) {
                long minutes = codeTime.until(present, ChronoUnit.MINUTES);
                if (!matchingCode.isActivated()) {
                    if (minutes < 5 || isOwnedBySuperUser(matchingCode)) {
                        matchingCode.setActivated(true);
                        activeService.save(matchingCode);
                        return true;
                    }
                    return false;
                }
                return hours <= 4 || isOwnedBySuperUser(matchingCode);
            }
        }
        return false;
    }

    public static boolean isOwnedBySuperUser(ActiveCode matchingCode) {
        return (matchingCode.getId().getUser().getRole().getName().equals(RolesEnum.ADMIN.role())
                || matchingCode.getId().getUser().getRole().getName().equals(RolesEnum.COORDINATOR.role())
                || matchingCode.getId().getUser().getRole().getName().equals(RolesEnum.SUPERADMIN.role()));
    }

}
