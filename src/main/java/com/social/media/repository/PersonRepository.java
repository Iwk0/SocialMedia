package com.social.media.repository;

import com.social.media.model.Person;
import com.social.media.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PersonRepository extends JpaRepository<Person, String> {

    Person findByEmail(String email);

    @Query(value = "select fr from Person p join p.friends fr where p.id=:id")
    Set<Person> findAllFriends(@Param(value = "id") String id);

    @Query(value = "select pi from Person p join p.pictures pi where p.id=:id")
    Set<Picture> findAllPictures(@Param(value = "id") String id);
}