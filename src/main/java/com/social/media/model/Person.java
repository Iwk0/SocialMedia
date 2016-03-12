package com.social.media.model;

import com.social.media.validation.MatchPassword;
import com.social.media.validation.UniqueEmail;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@MatchPassword
public class Person extends ParentEntity {

    @Column
    @Getter
    @Setter
    @Size(min = 3)
    private String firstName;

    @Column
    @Getter
    @Setter
    @Size(min = 3)
    private String lastName;

    @Column
    @Getter
    @Setter
    @Size(min = 3)
    private String surname;

    @Email
    @Column(unique = true)
    @Getter
    @Setter
    @UniqueEmail
    private String email;

    @Column
    @Getter
    @Setter
    @Size(min = 6, max = 36)
    private String password;

    @Transient
    @Getter
    @Setter
    @Size(min = 6, max = 36)
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