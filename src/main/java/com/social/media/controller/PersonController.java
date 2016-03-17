package com.social.media.controller;

import com.social.media.model.Person;
import com.social.media.model.Photo;
import com.social.media.service.PersonService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@Log4j
@Controller
public class PersonController {

    @Autowired
    @Qualifier(value = "personService")
    private PersonService personService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView preview(@PathVariable("id") String id) {
        log.info("Person preview id = " + id);

        ModelAndView modelAndView = new ModelAndView("/person/preview", "person", personService.findOne(id));
        modelAndView.addObject("friends", personService.findAllFriends(id));

        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("person") Person person, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Cannot register person");
            return "/login";
        }

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        person.setPassword(shaPasswordEncoder.encodePassword(person.getPassword(), ""));
        personService.save(person);

        log.info("Person is registered successfully");

        return "redirect:/login";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        ModelAndView modelAndView = new ModelAndView("/person/settings", "person", personService.findByEmail());
        modelAndView.addObject("picture", new Photo());
        return modelAndView;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String newSettings(@Valid @ModelAttribute("person") Person person) {
        return "";
    }

    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    @ResponseBody
    public String addFriend(@RequestParam(value = "id") String id) {
        personService.addFriend(id);
        return "SUCCESS";
    }

    @RequestMapping("/picture")
    public ResponseEntity<byte[]> getImage() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(IOUtils.toByteArray(personService.getPhoto()), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute(value = "picture") Photo photo,
                              @RequestParam("file") MultipartFile file) {
        try {
            photo.setImage(file.getBytes());
            personService.addPhoto(photo);
        } catch (IOException e) {
            log.error("IOException", e);
        }

        return "redirect:/settings";
    }
}