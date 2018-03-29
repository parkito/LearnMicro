
package com.parkito.learnmicro.monolith.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Auditable entity with created and modified timestamps.
 */
@MappedSuperclass
@EqualsAndHashCode
public abstract class AuditableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_timestamp", updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_timestamp")
    private Date modified;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @PrePersist
    public void prePersist() {
        created = new Date();
        preUpdate();
    }

    @PreUpdate
    public void preUpdate() {
        modified = new Date();
    }

}
