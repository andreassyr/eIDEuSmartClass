/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dmo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author nikos
 */
@Embeddable
public class ActiveCodePK  implements Serializable{

    protected long active_code_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    protected ClassRoom classRoom;

    public ActiveCodePK() {
    }

    public ActiveCodePK(User user, ClassRoom classRoom) {
        this.user = user;
        this.classRoom = classRoom;
    }

    public long getActive_code_id() {
        return active_code_id;
    }

    public void setActive_code_id(long active_code_id) {
        this.active_code_id = active_code_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.active_code_id ^ (this.active_code_id >>> 32));
        hash = 83 * hash + Objects.hashCode(this.user);
        hash = 83 * hash + Objects.hashCode(this.classRoom);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActiveCodePK other = (ActiveCodePK) obj;
        if (this.active_code_id != other.active_code_id) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.classRoom, other.classRoom)) {
            return false;
        }
        return true;
    }

   

}
