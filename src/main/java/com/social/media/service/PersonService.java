package com.social.media.service;

import com.social.media.model.Person;

import java.util.Set;

public interface PersonService {

    void addFriend(String id);

    Person findByEmail();

    Person findOne(String id);

    Person save(Person person);

    Set<Person> findAllFriends(String id);
}