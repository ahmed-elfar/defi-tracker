package com.xyvo.defi.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import java.sql.Timestamp;

@MappedSuperclass
abstract class Audited {

    @CreationTimestamp
    @Column(nullable = false)
    protected Timestamp created;

    @UpdateTimestamp
    @Column(nullable = false)
    protected Timestamp updated;

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
