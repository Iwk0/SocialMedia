package com.social.media.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EqualsAndHashCode(exclude = {"dateCreated", "dateLastModified"})
public class ParentEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Getter
    @Setter
    private String id;

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