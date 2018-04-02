/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 *
 * @author nikos
 */
public class EidasUser {

    //{"firstName":"ΑΝΔΡΕΑΣ, ANDREAS","eid":"GR/GR/ERMIS-11076669","familyName":"ΠΕΤΡΟΥ, PETROU","personIdentifier":"GR/GR/ERMIS-11076669","dateOfBirth":"1980-01-01"}
    private String profileName;
    private String eid;
    @JsonAlias({"currentGivenName", "firstName"})
    private String currentGivenName;
    @JsonAlias({"currentFamilyName", "familyName"})
    private String currentFamilyName;
    private String personIdentifier;
    private String dateOfBirth;

    public EidasUser() {
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getCurrentGivenName() {
        return currentGivenName;
    }

    public void setCurrentGivenName(String currentGivenName) {
        this.currentGivenName = currentGivenName;
    }

    public String getCurrentFamilyName() {
        return currentFamilyName;
    }

    public void setCurrentFamilyName(String currentFamilyName) {
        this.currentFamilyName = currentFamilyName;
    }

    public String getPersonIdentifier() {
        return personIdentifier;
    }

    public void setPersonIdentifier(String personIdentifier) {
        this.personIdentifier = personIdentifier;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
