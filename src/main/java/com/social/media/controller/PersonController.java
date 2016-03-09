package com.social.media.controller;

import com.social.media.model.Person;
import com.social.media.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView personPreview(@PathVariable("id") String id) {
        return new ModelAndView("/person/preview", "person", personRepository.findOne(Long.valueOf(id)));
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createPerson(@Valid @ModelAttribute("person") Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/login";
        }

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        person.setPassword(shaPasswordEncoder.encodePassword(person.getPassword(), ""));
        personRepository.save(person);

        return "redirect:/login";
    }
}