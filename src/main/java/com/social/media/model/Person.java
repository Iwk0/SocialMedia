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

    @Column(name = "first_name")
    @Getter
    @Setter
    @Size(min = 3)
    private String firstName;

    @Column(name = "last_name")
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

    @Lob
    @Column(name = "profile_picture")
    @Getter
    @Setter
    private byte[] profilePicture;

    @Column(name = "unique_name")
    @Getter
    @Setter
    private String uniqueName;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "person_to_friend",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")})
    private Set<Friend> friends;

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

    public void addFriend(Friend friend) {
        if (friends == null) {
            friends = new HashSet<>();
        }

        friends.add(friend);
    }

/*    public void addPerson(Friend friend) {
        if (persons == null) {
            persons = new HashSet<>();
        }

        persons.add(friend);
    }*/

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