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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPhotoService photoService;

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(@ModelAttribute("username") String username, @ModelAttribute("page") int page) {
        User user = userService.findUser(username);
        Page<Photo> photosPage = photoService.getPhotos(user, page);
        List<Photo> photos = photosPage.getContent();
        int countPages = photosPage.getTotalPages();
        ModelAndView modelAndView = new ModelAndView("sections/profile", "user", user);
        int subscribers = userService.countSubscribers(user);
        int subscriptions = userService.countSubscriptions(user);
        modelAndView.addObject("countSubscribers", subscribers);
        modelAndView.addObject("countSubscriptions", subscriptions);
        modelAndView.addObject("photos", photos);

        ArrayList<Integer> pages = new ArrayList<>();
        for (int i = 0; i < countPages; i++) {
            pages.add(i);
        }

        modelAndView.addObject("countPages", pages);
        modelAndView.addObject("page", page);
        return modelAndView;
    }
}
