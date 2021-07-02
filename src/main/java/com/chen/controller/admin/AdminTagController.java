package com.chen.controller.admin;

import com.github.pagehelper.PageInfo;
import com.chen.entity.Tag;
import com.chen.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "admin")
public class AdminTagController {


    @Autowired
    private TagService tagService;

    @RequestMapping("/tags")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       @RequestParam(defaultValue = "10",value = "pageSize") Integer pageSize,
                        Model model) {

        List<Tag> tags = tagService.listTag((pageNum-1)*pageSize, pageSize);
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/tags";
    }

    @RequestMapping("/tags/add")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @RequestMapping("/editTag/{id}")
    public String editInput(@PathVariable("id") int id, Model model) {
        model.addAttribute("tag", tagService.getTagById(id));
        return "admin/tags-input";
    }


    @RequestMapping(value = "/saveTag", method = RequestMethod.POST)
    public String post(Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        int res = tagService.saveTag(tag);
        if (res == 0) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }


    @RequestMapping(value = "/updateTag/{id}", method = RequestMethod.POST)
    public String editPost(Tag tag, BindingResult result, @PathVariable int id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        int res = tagService.updateTag(tag);
        if (res == 0) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @RequestMapping("/deleteTag/{id}")
    public String delete(@PathVariable("id") int id,RedirectAttributes attributes) {
        int res = tagService.deleteTagById(id);
        if(res == 1) attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
