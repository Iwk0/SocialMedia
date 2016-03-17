package com.social.media.model;

import com.social.media.validation.MatchPassword;
import com.social.media.validation.UniqueEmail;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

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

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name = "personId")}, inverseJoinColumns = {@JoinColumn(name = "friendId")})
    private Set<Person> friends;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<Picture> pictures;

    @Column
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column
    @Getter
    @Setter
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    public void addFriend(Person person) {
        if (friends == null) {
            friends = new HashSet<>();
        }

        friends.add(person);
    }

    public void addPicture(Picture picture) {
        if (pictures == null) {
            pictures = new HashSet<>();
        }

        pictures.add(picture);
    }

    public enum Role {
        ANONYMOUS, ADMIN, USER
    }
}