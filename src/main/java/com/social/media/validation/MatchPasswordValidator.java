package com.social.media.validation;

import com.social.media.model.Person;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, Object> {

    @Override
    public void initialize(MatchPassword matchPassword) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        Person person = (Person) obj;
        return person.getPassword().equals(person.getRawPassword());
    }
}