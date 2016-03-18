package com.social.media.controller;

import com.social.media.model.Person;
import com.social.media.service.PersonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    @ResponseBody
    public String addFriend(@RequestParam(value = "id") String id) {
        personService.addFriend(id);
        return "SUCCESS";
    }
}