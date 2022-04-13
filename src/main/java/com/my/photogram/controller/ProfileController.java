package com.my.photogram.controller;

import com.my.photogram.entity.Photo;
import com.my.photogram.entity.User;
import com.my.photogram.service.IPhotoService;
import com.my.photogram.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPhotoService photoService;

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(
            @ModelAttribute("username") String username,
            @ModelAttribute("page") int page,
            Principal principal) {
        User user = userService.findUser(username);
        Page<Photo> photosPage = photoService.getPhotos(user, page);
        List<Photo> photos = photosPage.getContent();
        int countPages = photosPage.getTotalPages();
        ModelAndView modelAndView = new ModelAndView("sections/profile", "user", user);
        int subscribers = userService.countSubscribers(user);
        int subscriptions = userService.countSubscriptions(user);
        modelAndView.addObject("countPhotos", photosPage.getTotalElements());
        modelAndView.addObject("countSubscribers", subscribers);
        modelAndView.addObject("countSubscriptions", subscriptions);
        modelAndView.addObject("photos", photos);

        if (!principal.getName().equals(username)) {
            boolean isSubscriber = userService.isSubscriber(userService.findUser(principal.getName()), user);
            modelAndView.addObject("isSubscriber", isSubscriber);
        }

        ArrayList<Integer> pages = new ArrayList<>();
        for (int i = 0; i < countPages; i++) {
            pages.add(i);
        }

        modelAndView.addObject("countPages", pages);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @RequestMapping(path = "/profile/subscribe", method = RequestMethod.GET)
    public ModelAndView subscribe(
            @ModelAttribute("username") String username,
            @ModelAttribute("page") int page,
            Principal principal
    ) {
        User subscriber = userService.findUser(principal.getName());
        User author = userService.findUser(username);
        userService.subscribe(subscriber, author);
        return profile(username, page, principal);
    }

    @RequestMapping(path = "/profile/unsubscribe", method = RequestMethod.GET)
    public ModelAndView unsubscribe(
            @ModelAttribute("username") String username,
            @ModelAttribute("page") int page,
            Principal principal
    ) {
        User subscriber = userService.findUser(principal.getName());
        User author = userService.findUser(username);
        userService.unsubscribe(subscriber, author);
        return profile(username, page, principal);
    }
}
