package com.social.media.service;

import com.social.media.model.Album;
import com.social.media.model.Person;
import com.social.media.model.Photo;
import com.social.media.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

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

    @Transactional(readOnly = false)
    public void addPhoto(Photo photo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByEmail(auth.getName());
        photo.setPerson(person);
        person.addPhoto(photo);
        personRepository.save(person);
    }

    public InputStream getPhoto() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByEmail(auth.getName());
        Set<Photo> photos = personRepository.findAllPhotos(person.getId());
        Photo pic = photos.iterator().next();
        return new ByteArrayInputStream(pic.getImage());
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

    @Transactional(readOnly = true)
    public Set<Person> findAllFriends(String id) {
        return personRepository.findAllFriends(id);
    }

    public String addAlbum() {
        Person person = new Person();
        Album album = new Album();
        album.setName("Name");
        album.setPerson(person);
        person.addAlbum(album);

        Photo photo = new Photo();
        photo.setPerson(person);
        photo.setName("Name");
        photo.setDescription("des");
        photo.setImage(null);

        album.addPhoto(photo);
        return "";
    }
}