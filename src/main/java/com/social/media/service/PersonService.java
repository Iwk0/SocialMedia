package com.social.media.service;

import com.social.media.model.Person;

public interface PersonService {

    String addFriend(String id, String email);

    String findFriend(String id, String email);

    Person findByEmail(String email);

    Person findOne(String id);

    Person save(Person person);
}