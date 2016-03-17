package com.social.media.service;

import com.social.media.model.Person;
import com.social.media.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "personService")
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional(readOnly = false)
    public void addFriend(String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByEmail(auth.getName());
        person.addFriend(personRepository.findOne(id));
        personRepository.save(person);
    }

    @Transactional(readOnly = true)
    public Person findByEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return personRepository.findByEmail(auth.getName());
    }

    @Transactional(readOnly = true)
    public Person findOne(String id) {
        return personRepository.findOne(id);
    }

    @Transactional(readOnly = false)
    public Person save(Person person) {
        return personRepository.save(person);
    }
}