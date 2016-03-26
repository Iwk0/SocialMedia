package com.social.media.service;

import com.social.media.model.Person;

public interface PersonService {

    Person findByEmail(String email);

    Person findOne(String id);

    Person save(Person person);
}