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

    @Column
    @Getter
    @Setter
    private String path;

    @Column
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Getter
    @Setter
    @OneToOne(mappedBy = "profilePicture")
    private Person profilePicture;
}