package com.social.media.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Person extends ParentEntity {

    @Column
    @Getter
    @Setter
    private String username;

    @Column
    @Getter
    @Setter
    private String firstName;

    @Column
    @Getter
    @Setter
    private String lastName;

    @Column
    @Getter
    @Setter
    private String surname;

    @Email
    @Column
    @Getter
    @Setter
    private String email;

    @Column
    @Getter
    @Setter
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Status status = Status.ACTIVE;

    @Column
    @Enumerated(value = EnumType.STRING)
    @Getter
    @Setter
    private Role role = Role.USER;

    public enum Role {
        ANONYMOUS, ADMIN, USER
    }
}