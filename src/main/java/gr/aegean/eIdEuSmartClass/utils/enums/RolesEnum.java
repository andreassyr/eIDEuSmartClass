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
public enum RolesEnum {

    ADMIN("admin"),
    BLACKLISTED("blacklisted"),
    COORDINATOR("coordinator"),
    SUPERADMIN("superadmin"),
    UNIDENTIFIED("unidentified"),
    UNREGISTERED("unregistered"),
    VIRTUALPARTICIPANT("virtualparticipant"),
    VISITOR("visitor");

    private String role;

    RolesEnum(String role) {
        this.role = role;
    }

    public String role() {
        return role;
    }
}
