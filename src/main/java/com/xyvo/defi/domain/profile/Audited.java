package com.xyvo.defi.domain.profile;

import com.xyvo.defi.utils.DomainUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import java.sql.Timestamp;


@MappedSuperclass
public abstract class Audited {

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

    public Audited removeNanos() {
        DomainUtils.timestampFormatter(created);
        DomainUtils.timestampFormatter(updated);
        return this;
    }
}
