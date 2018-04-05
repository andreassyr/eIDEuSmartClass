/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dmo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author nikos
 */
@Entity
@Table(name = "activecodes")
public class ActiveCode {

    @EmbeddedId
    private ActiveCodePK id;

    private String content;

    @Column(name = "granted_at")
    private LocalDateTime grantedAt;
    
    private boolean activated;

    public ActiveCode() {
    }

    public ActiveCodePK getId() {
        return id;
    }

    public void setId(ActiveCodePK id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getGrantedAt() {
        return grantedAt;
    }

    public void setGrantedAt(LocalDateTime grantedAt) {
        this.grantedAt = grantedAt;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    
    

}
