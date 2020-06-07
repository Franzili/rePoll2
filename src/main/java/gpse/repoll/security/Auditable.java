package gpse.repoll.security;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedBy
    @OneToOne
    protected U creator;

    @LastModifiedBy
    @OneToOne
    protected U lastEditor;

    @CreatedDate
    @Column
    protected LocalDateTime creationTime;

    @LastModifiedDate
    @Column
    protected LocalDateTime lastEditTime;

    public U getCreator() {
        return creator;
    }

    public void setCreator(U createdBy) {
        this.creator = createdBy;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public U getLastEditor() {
        return lastEditor;
    }

    public void setLastEditor(U lastModifiedBy) {
        this.lastEditor = lastModifiedBy;
    }

    public LocalDateTime getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(LocalDateTime lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
