package com.chen.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chen.entity.Blog;
import com.chen.entity.Comment;
import com.chen.entity.Tag;
import com.chen.entity.Type;
import com.chen.service.BlogService;
import com.chen.service.CommentService;
import com.chen.service.TagService;
import com.chen.service.TypeService;
import com.chen.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    // 分页查询博客列表
    @RequestMapping("/findAllBlog.do")
    public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @RequestParam(defaultValue = "10",value = "pageSize") Integer pageSize,
                        Model model) {

        PageHelper.startPage(pageNum, pageSize);

        List<Blog> allBlog = blogService.listBlogs((pageNum-1)*pageSize, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);

        List<Type> types = typeService.listTypeTop(6);
        List<Tag> tags = tagService.listTagTop(10);
        List<Blog> recommendedBlogs = blogService.listRecommendedBlogs();

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        model.addAttribute("recommendBlogs", recommendedBlogs);

        return "index";
    }

    //最新博客列表
    @RequestMapping(value = "/footer/newblog")
    public String newblogs(Model model) {
        List<Blog> newBlogs = blogService.listNewBlogs();
        model.addAttribute("newblogs", newBlogs);
        System.out.println("最新博客:===>"+newBlogs);
        return "index::newblogList";
    }


    //    搜索博客
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Model model,
                         @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam(defaultValue = "10",value = "pageSize") Integer pageSize,
                         @RequestParam("query") String query) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> Blog = blogService.listBlogBySearch(query);
        PageInfo<Blog> pageInfo = new PageInfo<>(Blog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    // 跳转指定博客的详情页面
    @RequestMapping(value = "/{id}")
    public String blog(@PathVariable int id, Model model) {
        Blog blog = blogService.getBlogById(id);
        List<Comment> comments = commentService.listCommentByBlogId(id);
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));//把markdown格式的文本转换为html以供前台显示
        model.addAttribute("comments", comments);
        model.addAttribute("blog", blog);
        blogService.updateView(blog.getId());
        return "blog";
    }

    //    博客信息
    @RequestMapping(value = "/footer/blogmessage")
    public String blogMessage(Model model){
        int blogTotal = blogService.getBlogTotal();
        int blogViewTotal = blogService.getBlogViewTotal();
        int blogCommentTotal = blogService.getBlogCommentTotal();
        int blogMessageTotal = blogService.getBlogMessageTotal();

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);
        model.addAttribute("blogCommentTotal",blogCommentTotal);
        model.addAttribute("blogMessageTotal",blogMessageTotal);
        return "index :: blogMessage";
    }
}
