package com.social.media.service;

import com.social.media.model.Person;
import com.social.media.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(value = "personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional(readOnly = false)
    public void addFriend(String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByEmail(auth.getName());
        person.addFriend(personRepository.findOne(id));
        personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Person findByEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return personRepository.findByEmail(auth.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public Person findOne(String id) {
        return personRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Person> findAllFriends(String id) {
        return personRepository.findAllFriends(id);
    }
}