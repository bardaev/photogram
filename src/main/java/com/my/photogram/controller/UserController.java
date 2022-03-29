package com.my.photogram.controller;

import com.my.photogram.entity.User;
import com.my.photogram.service.IUserService;
import com.my.photogram.validation.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String general(Authentication authentication) {
        return "general";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        return new ModelAndView("registration", "user",  new User());
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid User user, final BindingResult bindingResult, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("registration", "user", user);
        }
        try {
            if (user.getId() == null) {
                userService.registerNewUser(user);
                redirect.addFlashAttribute("success", "Successfully created new user!");
            }
        } catch (UsernameExistException e) {
            redirect.addFlashAttribute("userAlreadyExists", "User already exists!");
        }
        return new ModelAndView("redirect:/login");
    }
}
