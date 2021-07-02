package com.chen.controller;

import com.chen.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/archives")
public class ArchiveController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/findAllBlog.do")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlogByYear());
        model.addAttribute("blogCount", blogService.getBlogTotal());
        return "archives";
    }
}
