package com.chen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/about")
public class AboutMeController {

    @RequestMapping(value = "/showme.do")
    public String showMe() {
        return "about";
    }
}
