package com.social.media.controller;

import com.social.media.model.Album;
import com.social.media.model.ParentEntity;
import com.social.media.model.Person;
import com.social.media.model.Photo;
import com.social.media.service.PersonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Log4j
@Controller
public class PersonController {

    @Autowired
    @Qualifier(value = "personService")
    private PersonService personService;

    private SimpMessagingTemplate template;

    @Autowired
    public PersonController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    public ModelAndView preview(@PathVariable("id") String id, Principal principal) {
        log.info("Person preview id = " + id);

        return new ModelAndView("/person/preview", "person", personService.findOne(id));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("person") Person person, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Cannot register person");
            return "/login";
        }

        try {
            final String IMAGE_PATH = System.getProperty("user.dir") +
                    File.separator + "src" + File.separator + "main" +
                    File.separator + "webapp" + File.separator + "resources" +
                    File.separator + "images" + File.separator + person.getGender() + ".jpg";

            Path path = Paths.get(IMAGE_PATH);
            byte[] data = Files.readAllBytes(path);

            Photo profilePicture = new Photo();
            profilePicture.setImage(data);
            profilePicture.setProfilePicture(person);

            Album album = new Album();
            album.setName("Profile picture");
            album.addPhoto(profilePicture);

            profilePicture.setAlbum(album);

            person.addAlbum(album);
            person.setProfilePicture(profilePicture);
        } catch (IOException e) {
            log.error("IOException", e);
        }

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        person.setPassword(shaPasswordEncoder.encodePassword(person.getPassword(), ""));
        personService.save(person);

        log.info("Person is registered successfully");

        return "/login";
    }

    @MessageMapping("/friend")
    public void acceptFriend(ParentEntity model, Principal principal) {
        log.info("Message receive");
        template.convertAndSendToUser(personService.findOne(model.getId()).getEmail(), "/queue/acceptFriend", "friendRequest");
    }
}