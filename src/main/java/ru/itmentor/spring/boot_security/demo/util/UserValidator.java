package ru.itmentor.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserDetailsServiceImp;
import ru.itmentor.spring.boot_security.demo.service.UserValidService;

@Component
public class UserValidator implements Validator {
    private final UserValidService userValidService;
    private final UserDetailsServiceImp userDetailsService;

    @Autowired
    public UserValidator(UserValidService userValidService, UserDetailsServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.userValidService = userValidService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
       User user = (User) target;
//       if (!userValidService.equals(user)) return;
        try {
            userDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

       errors.rejectValue("username", "Человек с таким именем пользователя уже существует");
    }
}