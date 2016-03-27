package com.social.media.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Photo extends ParentEntity {

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
    @JoinColumn(name = "personId")
    @ManyToOne
    private Person person;

    @Getter
    @Setter
    @JoinColumn(name = "albumId")
    @ManyToOne
    private Album album;

    @Getter
    @Setter
    @OneToOne(mappedBy = "profilePicture")
    private Person profilePicture;
}