package com.my.photogram.controller;

import com.my.photogram.entity.Photo;
import com.my.photogram.entity.User;
import com.my.photogram.service.IPhotoService;
import com.my.photogram.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class ProfileController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPhotoService photoService;

    @RequestMapping(path = "/profile/{username}", method = RequestMethod.GET)
    public ModelAndView profile(
            @PathVariable("username") String username,
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

    @RequestMapping(path = "/profile/{username}/edit", method = RequestMethod.GET)
    public ModelAndView getEditProfile(@PathVariable("username") String username) {
        User user = userService.findUser(username);
        return new ModelAndView("sections/editProfile", "user", user);
    }

    @RequestMapping(path = "/profile/{username}/edit", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ModelAndView editProfile(
            @PathVariable("username") String username,
            @RequestParam("file") MultipartFile multipartFile,
            User user,
            Principal principal,
            RedirectAttributes redirectAttributes) throws IOException {

        String currentUser = principal.getName();
        if (!currentUser.equals(username)) {
            ModelAndView modelAndView = new ModelAndView("redirect:/profile/" + username + "/edit");
            redirectAttributes.addFlashAttribute("err", "Not your profile");
            return modelAndView;
        }

        if (!multipartFile.isEmpty() && !Objects.requireNonNull(multipartFile.getContentType()).startsWith("image")) {
            ModelAndView modelAndView = new ModelAndView("redirect:/profile/" + username + "/edit");
            redirectAttributes.addFlashAttribute("err", "Bad file");
            return modelAndView;
        }

        if (user.getUsername() != null && (userService.findUser(user.getUsername()) == null || userService.findUser(user.getUsername()).getUsername().equals(user.getUsername()))) {
            User updatedUser = userService.findUser(username);
            updatedUser.setUsername(user.getUsername());
            if (!multipartFile.isEmpty()) {
                updatedUser.setAvatar(multipartFile.getBytes());
            }
            updatedUser.setDescription(user.getDescription());
            userService.updateUser(updatedUser);

            return profile(user.getUsername(), 0, principal);
        }

        return profile(username, 0, principal);
    }

    @RequestMapping(path = "/profile/{username}/subscribe", method = RequestMethod.GET)
    public ModelAndView subscribe(
            @PathVariable("username") String username,
            @ModelAttribute("page") int page,
            Principal principal
    ) {
        User subscriber = userService.findUser(principal.getName());
        User author = userService.findUser(username);
        userService.subscribe(subscriber, author);
        return profile(username, page, principal);
    }

    @RequestMapping(path = "/profile/{username}/unsubscribe", method = RequestMethod.GET)
    public ModelAndView unsubscribe(
            @PathVariable("username") String username,
            @ModelAttribute("page") int page,
            Principal principal
    ) {
        User subscriber = userService.findUser(principal.getName());
        User author = userService.findUser(username);
        userService.unsubscribe(subscriber, author);
        return profile(username, page, principal);
    }
}
