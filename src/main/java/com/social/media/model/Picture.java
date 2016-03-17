package com.social.media.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Picture extends ParentEntity {

    @Column
    @Getter
    @Setter
    private String name;

    @Lob
    @Column
    @Getter
    @Setter
    private byte[] image;

    @Column
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @JoinColumn(name = "pictureId")
    @ManyToOne
    private Person person;
}