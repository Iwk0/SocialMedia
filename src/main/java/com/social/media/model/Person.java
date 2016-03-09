package com.social.media.model;

import com.social.media.validation.MatchPassword;
import com.social.media.validation.UniqueEmail;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

@Entity
@MatchPassword
public class Person extends ParentEntity {

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
    @UniqueEmail
    private String email;

    @Column
    @Getter
    @Setter
    private String password;

    @Transient
    @Getter
    @Setter
    private String rawPassword;

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