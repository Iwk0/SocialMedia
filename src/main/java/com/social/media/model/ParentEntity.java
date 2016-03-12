package com.social.media.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EqualsAndHashCode(exclude = {"dateCreated", "dateLastModified"})
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
}