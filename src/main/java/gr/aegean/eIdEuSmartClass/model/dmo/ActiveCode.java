/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.dmo;

import java.time.LocalDate;
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
    private LocalDate grantedAt;

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

    public LocalDate getGrantedAt() {
        return grantedAt;
    }

    public void setGrantedAt(LocalDate grantedAt) {
        this.grantedAt = grantedAt;
    }

}
