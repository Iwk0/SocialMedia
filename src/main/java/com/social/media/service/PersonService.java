package com.social.media.service;

import com.social.media.model.Person;

import java.util.Set;

public interface PersonService {

    String addFriend(String id);

    String findFriend(String id);

    Person findByEmail();

    Person findOne(String id);

    Person save(Person person);

    Set<Person> findAllFriends(String id);
}