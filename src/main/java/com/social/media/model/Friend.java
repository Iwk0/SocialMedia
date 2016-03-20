package com.social.media.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Friend extends ParentEntity {

    @Column
    @Getter
    @Setter
    private boolean friendAccepted;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "personId")
    private Person person;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "friendId")
    private Person friend;
}