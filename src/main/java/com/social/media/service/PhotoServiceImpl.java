package com.social.media.service;

import com.social.media.model.Person;
import com.social.media.model.Photo;
import com.social.media.repository.PersonRepository;
import com.social.media.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Service(value = "photoService")
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    @Transactional(readOnly = false)
    public void addPhoto(Photo photo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByEmail(auth.getName());
        photo.setPerson(person);
        person.addPhoto(photo);
        personRepository.save(person);
    }

    @Override
    public InputStream getPhoto(String id) {
        Person person = personRepository.findOne(id);
        return new ByteArrayInputStream(person.getProfilePicture().getImage());
    }

    @Override
    public List<Byte> findAll(String personId) {
        return photoRepository.findAllPhotos(personId);
    }
}