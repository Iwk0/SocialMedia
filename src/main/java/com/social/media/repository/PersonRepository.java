package com.social.media.repository;

import com.social.media.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, String> {

    Person findByEmail(String email);

    @Query(value = "select p from Person p where p.uniqueName=:id or p.id=:id")
    Person findByIdOrName(@Param(value = "id") String id);
}