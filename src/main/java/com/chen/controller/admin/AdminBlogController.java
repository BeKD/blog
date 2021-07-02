package com.chen.controller.admin;


import com.github.pagehelper.PageInfo;
import com.chen.entity.Blog;
import com.chen.entity.Tag;
import com.chen.entity.User;
import com.chen.service.BlogService;
import com.chen.service.TagService;
import com.chen.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by limi on 2017/10/15.
 */
@Controller
@RequestMapping("/admin")
public class AdminBlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @RequestParam(defaultValue = "10",value = "pageSize") Integer pageSize,
                        Model model) {

        model.addAttribute("types", typeService.listAllType());
        List<Blog> blogs = blogService.listBlogs((pageNum-1)*pageSize, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        System.out.println("blogs===>"+blogs);
        model.addAttribute("pageInfo", pageInfo);
        return LIST;
    }

    // 搜索 by title, type
    @RequestMapping(value = "/blogs/search", method = RequestMethod.POST)
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         @RequestParam(defaultValue = "10",value = "pageSize") Integer pageSize,
            @RequestParam("title") String title,
            @RequestParam("typeId") Integer typeId,
            Model model) {

        List<Blog> Blog = blogService.listBlogBySearchTitleOrType(title, typeId);
        PageInfo<Blog> pageInfo = new PageInfo<>(Blog);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs :: blogList";
    }


    /**
     * 跳转到编写博客页面
     */
    @RequestMapping("/blogs/add")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    /**
     * 编辑blog
     */
    @RequestMapping("/editBlog/{id}")
    public String editInput(@PathVariable("id") int id, Model model) {

        setTypeAndTag(model);

        Blog blog = blogService.getBlogById(id);
        blog.initTagIds();
        System.out.println("edit ===> tagids---"+blog.getTagIds());

        //编辑之前先清除分类和标签信息
        typeService.reduceArticleCount(blog.getType().getId());//分类的文章数-1
        tagService.reduceArctleCount(blog.getTags());//标签的文章数-1
        tagService.clearTags(blog.getId());          //清空blog_tags表中对应的表项

        model.addAttribute("blog", blog);
        return INPUT;
    }
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listAllType());
        model.addAttribute("tags", tagService.listAllTag());
    }

    /**
     * 保存blog
     */
    @RequestMapping(value = "/saveBlog", method = RequestMethod.POST)
    public String saveBlog(Blog blog, RedirectAttributes attributes, HttpSession session) {

        if(blog.getType() == null) System.out.println("Type 赋值失败!");

        tagIdsToTagList(blog);
        if(blog.getTags() == null) System.out.println("Tag 赋值失败!");

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getTypeById(blog.getType().getId()));

        int res = 0;
        if (blog.getId() == null) {
            blog.setId(blogService.getMaxBlogId() + 1);//取得当前数据库最大的blog id, 加1为新博客的id赋值
            res =  blogService.saveBlog(blog);
        } else {
            res = blogService.updateBlog(blog);
        }

        if (res == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
            System.out.println("文章添加成功!");
            typeService.addArticleCount(blog.getType().getId());  //分类的文章数+1
            tagService.addArctleCount(blog.getTags());          //标签的文章数+1
            tagService.addBlogTags(blog.getId(), blog.getTags()); //blog-tags一对多关系的插入
        }
        return REDIRECT_LIST;
    }
    //把前端的tag多选字符串转为Tag列表存入blog对象
    private void tagIdsToTagList(Blog blog){
        System.out.println("ids ================> Tags  ------->  "+blog.getTagIds());
        String tagIds = blog.getTagIds();
        List<String> result = Arrays.asList(tagIds.split(","));
        List<Tag> tags = new ArrayList<>();
        for(String id : result){
           Tag tag = tagService.getTagById(Integer.valueOf(id));
           tags.add(tag);
           System.out.println("tagId = "+Integer.valueOf(id));
        }
        blog.setTags(tags);
        System.out.println(blog.getTags());
        System.out.println("ids ===============> Tags");
    }

    /**
     * 删除blog
     */
    @RequestMapping("/deleteBlog/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attributes) {

        //删除分类和标签的文章数量
        Blog blog = blogService.getBlogById(id);
        typeService.reduceArticleCount(blog.getType().getId()); //相关分类的文章数量-1
        tagService.reduceArctleCount(blog.getTags());   //相关标签的文章数量-1
        tagService.clearTags(blog.getId());     //相关blog-tags关系的清除

        int res = blogService.deleteBlogById(id);
        if(res == 1) attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }

}
