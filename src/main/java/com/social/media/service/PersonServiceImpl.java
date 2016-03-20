package com.social.media.service;

import com.social.media.model.Person;
import com.social.media.repository.FriendRepository;
import com.social.media.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(value = "personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Override
    @Transactional(readOnly = false)
    public String addFriend(String id, String email) {
        Person person = personRepository.findByEmail(email);

        if (personRepository.findSpecificFriend(person.getId(), id) != null) {
            return "FAIL";
        }

        person.addFriend(friendRepository.findOne(id));
        personRepository.save(person);
        return "SUCCESS";
    }

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

    @Override
    @Transactional(readOnly = true)
    public Set<Person> findAllFriends(String id) {
        return personRepository.findAllFriends(id);
    }

    @Override
    public String findFriend(String id, String email) {
        Person person = personRepository.findByEmail(email);
        return personRepository.findSpecificFriend(person.getId(), id);
    }
}