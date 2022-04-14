package com.my.photogram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ServiceController {

    @Autowired
    @Qualifier(value = "logo")
    private byte[] logo;

    @RequestMapping(
            path = "/logo",
            produces = MediaType.IMAGE_PNG_VALUE,
            method = RequestMethod.GET)
    public @ResponseBody byte[] logo() {
        return logo;
    }
}
