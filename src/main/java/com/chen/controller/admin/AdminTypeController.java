package com.chen.controller.admin;

import com.github.pagehelper.PageInfo;
import com.chen.entity.Type;
import com.chen.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminTypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @RequestParam(defaultValue = "10",value = "pageSize") Integer pageSize,
                        Model model) {
        List<Type> types = typeService.listType((pageNum-1)*pageSize, pageSize);
        PageInfo<Type> pageInfo = new PageInfo<>(types);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    /**
     * 去新增type页面
     */
    @RequestMapping(value = "/types/add")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 编辑指定type
     */
    @RequestMapping(value = "/editType/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("type", typeService.getTypeById(id));
        return "admin/types-input";
    }

    /**
     * 保存type
     */
    @RequestMapping(value = "/saveType" , method = RequestMethod.POST)
    public String saveType(Type type,  BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        int res = typeService.saveType(type);
        if (res == 0) {
            attributes.addFlashAttribute("message", "添加失败");
        } else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 更新type
     */
    @RequestMapping(value = "/updateType/{id}", method = RequestMethod.POST)
    public String updateType(Type type, BindingResult result, @PathVariable int id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        int res = typeService.updateType(type);
        if (res == 0) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除type
     */
    @RequestMapping(value = "/deleteType/{id}")
    public String deleteType(@PathVariable("id") int id,RedirectAttributes attributes) {
        int res = typeService.deleteType(id);
        if(res == 1) attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
