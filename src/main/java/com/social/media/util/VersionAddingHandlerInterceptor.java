package com.social.media.util;

import com.social.media.repository.PersonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VersionAddingHandlerInterceptor extends HandlerInterceptorAdapter {

    public static final String VERSION_MODEL_ATTRIBUTE_NAME = "mainAccount";

    private PersonRepository personRepository;

    public VersionAddingHandlerInterceptor(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.getModelMap().
                addAttribute(VERSION_MODEL_ATTRIBUTE_NAME,
                        personRepository.findByEmail(auth.getName()));
    }
}