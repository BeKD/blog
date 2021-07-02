package com.chen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 首页控制器
 */

@Controller
public class IndexController {

    /**
     * 首页
     */
    @RequestMapping("/")
    public String index(){
        return "redirect:blog/findAllBlog.do";
    }

}
