package com.social.media.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class ParentEntity implements Serializable {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column
    @Getter
    @Setter
    private Date dateCreated;

    @Column
    @Getter
    @Setter
    private Date dateLastModified;

    @PreUpdate
    public void preUpdate() {
        this.dateLastModified = new Date();
    }

    @PrePersist
    public void perCreate() {
        this.dateCreated = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentEntity that = (ParentEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}