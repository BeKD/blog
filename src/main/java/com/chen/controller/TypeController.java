package com.chen.controller;

import com.github.pagehelper.PageInfo;
import com.chen.entity.Blog;
import com.chen.entity.Type;
import com.chen.service.BlogService;
import com.chen.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /**
     * 分页查询博客列表 by 分类
     * @param pageNum 当前页码
     * @param pageSize 页面可装载的博客数量
     */
    @RequestMapping(value = "/findAllBlog.do/{typeId}")
    public String getBlogsByTypeId(@PathVariable("typeId") Integer typeId,
                                   @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                                   @RequestParam(defaultValue = "10",value = "pageSize") Integer pageSize,
                                   Model model){

        int typeTotal = typeService.getTypeTotal();
        List<Type> types = typeService.listTypeTop(typeTotal);

        //设置默认为active的分类
        if(typeTotal > 0){
            if(typeId == -1) typeId = types.get(0).getId();
        }
        else{
            return "types";
        }

        List<Blog> allBlog = blogService.listBlogsByTypeId(typeId, pageNum, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);

        model.addAttribute("types", types);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId", typeId);

        return "types";
    }


}
