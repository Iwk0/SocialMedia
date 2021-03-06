package com.social.media.model;

import com.social.media.validation.MatchPassword;
import com.social.media.validation.UniqueEmail;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@MatchPassword
public class Person extends ParentEntity {

    @Getter
    @Setter
    @Size(min = 3)
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Setter
    @Size(min = 3)
    @Column(name = "last_name")
    private String lastName;

    @Column
    @Getter
    @Setter
    @Size(min = 3)
    private String surname;

    @Email
    @Getter
    @Setter
    @UniqueEmail
    @Column(unique = true)
    private String email;

    @Column
    @Getter
    @Setter
    @Size(min = 6, max = 36)
    private String password;

    @Getter
    @Setter
    @Transient
    @Size(min = 6, max = 36)
    private String rawPassword;

    @Getter
    @Setter
    @Column(name = "unique_name")
    private String uniqueName;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture_id")
    private Photo profilePicture;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<Photo> photos;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<Album> albums;

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

    @Getter
    @Setter
    @Column
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public void addPhoto(Photo photo) {
        if (photos == null) {
            photos = new HashSet<>();
        }

        photos.add(photo);
    }

    public void addAlbum(Album album) {
        if (albums == null) {
            albums = new HashSet<>();
        }

        albums.add(album);
    }

    public enum Role {
        ANONYMOUS, ADMIN, USER
    }

    public enum Gender {
        MALE, FEMALE
    }
}