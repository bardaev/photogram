package com.my.photogram.controller;

import com.my.photogram.entity.User;
import com.my.photogram.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    @Autowired
    private IUserService userService;

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(@ModelAttribute("username") String username) {
        User user = userService.findUser(username);
        ModelAndView modelAndView = new ModelAndView("sections/profile", "user", user);
        int subscribers = userService.countSubscribers(user);
        int subscriptions = userService.countSubscriptions(user);
        modelAndView.addObject("countSubscribers", subscribers);
        modelAndView.addObject("countSubscriptions", subscriptions);
        return modelAndView;
    }
}
