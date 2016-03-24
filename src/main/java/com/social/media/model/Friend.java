package com.social.media.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Friend extends ParentEntity {

    @Column
    @Getter
    @Setter
    private boolean friendAccepted;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "friends")
    private Set<Person> friends;
}