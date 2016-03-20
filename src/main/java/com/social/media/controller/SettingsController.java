package com.social.media.controller;

import com.social.media.model.Person;
import com.social.media.model.Photo;
import com.social.media.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class SettingsController {

    @Autowired
    @Qualifier(value = "personService")
    private PersonService personService;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settings(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("/person/settings", "person", personService.findByEmail(principal.getName()));
        modelAndView.addObject("picture", new Photo());
        return modelAndView;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String newSettings(@Valid @ModelAttribute("person") Person person) {
        return "";
    }
}