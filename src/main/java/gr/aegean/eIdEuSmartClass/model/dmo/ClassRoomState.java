/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dmo;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author nikos
 */
@Entity
@Table(name = "classroomstates")
public class ClassRoomState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private long statusId;
    
    private String name;
    
    @OneToMany(mappedBy = "state")
    private Set<ClassRoom> classRooms;

    public ClassRoomState() {
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the classRooms
     */
    public Set<ClassRoom> getClassRooms() {
        return classRooms;
    }

    /**
     * @param classRooms the classRooms to set
     */
    public void setClassRooms(Set<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }

}
