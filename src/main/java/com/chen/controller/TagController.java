package com.chen.controller;

import com.github.pagehelper.PageInfo;
import com.chen.entity.Blog;
import com.chen.entity.Tag;
import com.chen.service.BlogService;
import com.chen.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;


    /**
     * 分页查询博客列表 by 标签
     * @param pageNum 当前页码
     * @param pageSize 页面可装载的博客数量
     */
    @RequestMapping(value = "/findAllBlog.do/{tagId}")
    public String getBlogsByTagId(@PathVariable("tagId") Integer tagId,
                                  @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                                  @RequestParam(defaultValue = "10",value = "pageSize") Integer pageSize,
                                  Model model){

        int tagTotal = tagService.getTagTotal();
        List<Tag> tags = tagService.listTagTop(tagTotal);

        //设置默认为active的标签
        if(tagTotal > 0&&tagId == -1){
            tagId = tags.get(0).getId();
        }

        List<Blog> allBlog = blogService.listBlogsByTagId(tagId, pageNum, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);

        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTagId", tagId);
        return "tags";
    }


}
