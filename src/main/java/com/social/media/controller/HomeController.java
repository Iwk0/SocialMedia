package com.social.media.controller;

import com.social.media.model.Person;
import com.social.media.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        return new ModelAndView("/login", "person", new Person());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ModelAndView("/index", "person", personRepository.findByEmail(auth.getName()));
    }
}