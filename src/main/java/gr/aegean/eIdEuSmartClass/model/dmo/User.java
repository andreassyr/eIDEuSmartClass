/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dmo;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Dante
 */
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name = "eIDAS_id")
    private String eid;
    @Column(name = "name")
    private String currentGivenName;
    @Column(name = "surname")
    private String currentFamilyName;

    /**
     * new columns!!
     */
    private String email;
    private String mobile;
    private String affiliation;
    private String country;
    @Column(name = "ad_id")
    private String adId;

    @Column(name = "principal_name")
    private String principal;

    @Column(name = "eng_name")
    private String engName;
    @Column(name = "eng_surname")
    private String engSurname;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "last_login")
    private Date lastLogin;

    protected User() {
    }

    public User(Role role, String eIDAS_id, String name, String surname, String email, String mobile,
            String affiliation, String country, Gender gender, LocalDate birthday, Date lastLogin,
            String engName, String engSurname) {

        this.role = role;
        this.eid = eIDAS_id;
        this.currentGivenName = name;
        this.currentFamilyName = surname;
        this.email = email;
        this.mobile = mobile;
        this.affiliation = affiliation;
        this.country = country;
        this.gender = gender;
        this.dateOfBirth = birthday;
        this.lastLogin = lastLogin;
        this.engName = engName;
        this.engSurname = engSurname;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the eIDAS_id
     */
    public String getEid() {
        return eid;
    }

    /**
     * @param eid the eIDAS_id to set
     */
    public void setEid(String eid) {
        this.eid = eid;
    }

    /**
     * @return the name
     */
    public String getCurrentGivenName() {
        return currentGivenName;
    }

    /**
     * @param name the name to set
     */
    public void setCurrentGivenName(String name) {
        this.currentGivenName = name;
    }

    /**
     * @return the surname
     */
    public String getCurrentFamilyName() {
        return currentFamilyName;
    }

    /**
     * @param surname the surname to set
     */
    public void setCurrentFamilyName(String surname) {
        this.currentFamilyName = surname;
    }

    /**
     * @return the birthday
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the birthday to set
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the lastLogin
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * @param lastLogin the lastLogin to set
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        String result = String
                .format("User [id %d, "
                        + "name='%s',"
                        + " eIDASId='%s', "
                        + " birthday='%s', "
                        + " lastLogin='%s']",
                        this.id, this.currentGivenName, this.eid, this.dateOfBirth.toString(), this.lastLogin.toString());

        return result;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getEngSurname() {
        return engSurname;
    }

    public void setEngSurname(String engSurname) {
        this.engSurname = engSurname;
    }

}
