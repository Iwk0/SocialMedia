package com.social.media.service;

import com.social.media.model.Person;
import com.social.media.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional(readOnly = true)
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Person findOne(String id) {
        return personRepository.findByIdOrName(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Person save(Person person) {
        return personRepository.save(person);
    }
}