package com.social.media.controller;

import com.social.media.model.Person;
import com.social.media.repository.PersonRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Log4j
@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView preview(@PathVariable("id") String id) {
        log.info("Person preview id = " + id);
        return new ModelAndView("/person/preview", "person", personRepository.findOne(id));
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("person") Person person, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Cannot register person");
            return "/login";
        }

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        person.setPassword(shaPasswordEncoder.encodePassword(person.getPassword(), ""));
        personRepository.save(person);
        log.info("Person is registered successfully");

        return "redirect:/login";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ModelAndView("/person/settings", "person", personRepository.findByEmail(auth.getName()));
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String newSettings(@Valid @ModelAttribute("person") Person person) {
        return "";
    }

    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public String addFriend(@RequestParam(value = "id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByEmail(auth.getName());
        person.addFriend(personRepository.findOne(id));
        personRepository.save(person);

        return "redirect:/";
    }
}