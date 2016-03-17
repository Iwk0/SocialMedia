package com.social.media.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Album extends ParentEntity {

    @Column
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    @Getter
    @Setter
    @OneToMany(mappedBy = "album")
    private Set<Photo> photos;

    public void addPhoto(Photo photo) {
        if (photos == null) {
            photos = new HashSet<>();
        }

        photos.add(photo);
    }
}