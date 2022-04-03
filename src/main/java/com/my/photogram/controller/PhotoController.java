package com.my.photogram.controller;

import com.my.photogram.entity.Photo;
import com.my.photogram.entity.User;
import com.my.photogram.service.IPhotoService;
import com.my.photogram.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

@Controller
public class PhotoController {

    @Autowired
    private IPhotoService photoService;

    @Autowired
    private IUserService userService;

    @RequestMapping(path = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadForm() {
        return new ModelAndView("sections/upload", "photo", new Photo());
    }

    @RequestMapping(path = "/uploadPhoto", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String uploadPhoto(@RequestParam("file") MultipartFile multipartFile,
                              Photo photo,
                              RedirectAttributes redirectAttributes) throws IOException, NullPointerException {

        if (!Objects.requireNonNull(multipartFile.getContentType()).startsWith("image")) {
            throw new IOException();
        }
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findUser(username);
        photo.setUser(user);
        photo.setPhoto(multipartFile.getBytes());
        photoService.uploadPhoto(photo);

        redirectAttributes.addFlashAttribute("success", "Photo uploaded successfully");
        return "redirect:/upload";
    }

    @ExceptionHandler(value = {IOException.class, NullPointerException.class})
    public String handleError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("badFile", "Bad file");
        return "redirect:/upload";
    }
}
