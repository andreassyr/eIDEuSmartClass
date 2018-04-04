/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.enums;

/**
 *
 * @author nikos
 */
public enum RoomStatesEnum {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    RESTRICTED("Restricted");

    private String status;

    RoomStatesEnum(String status) {
        this.status = status;
    }

    public String state() {
        return status;
    }
}
