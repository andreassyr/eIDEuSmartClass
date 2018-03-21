/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Dante
 */
@Entity
@Table(name = "Roles")
public class Role {

    private int id;

    private String name;
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {

    }

    /**
     * @return the id
     */
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the users
     */
    @OneToMany(mappedBy = "role_id", cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        String result = String.format(
                "Role [id=%d, name='%s']%n", this.id, this.name);

        if (this.users != null) {
            for(User user : this.users){}
        }

        return result;
    }
}
