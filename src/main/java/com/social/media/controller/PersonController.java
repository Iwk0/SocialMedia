package com.social.media.controller;

import com.social.media.model.Person;
import com.social.media.service.PersonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView preview(@PathVariable("id") String id) {
        log.info("Person preview id = " + id);

        ModelAndView modelAndView = new ModelAndView("/person/preview", "person", personService.findOne(id));
        modelAndView.addObject("friends", personService.findAllFriends(id));
        modelAndView.addObject("addFriend", personService.findFriend(id));

        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
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

            person.setProfilePicture(data);
        } catch (IOException e) {
            log.error("IOException", e);
        }

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        person.setPassword(shaPasswordEncoder.encodePassword(person.getPassword(), ""));
        personService.save(person);

        log.info("Person is registered successfully");

        return "redirect:/login";
    }

    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    @ResponseBody
    public String addFriend(@RequestParam(value = "id") String id) {
        return personService.addFriend(id);
    }

    @MessageMapping("/friend")
    public void acceptFriend(String id, Principal principal) {
        template.convertAndSendToUser("ivomishev@gmail.com", "/acceptFriend", "test");
    }
}