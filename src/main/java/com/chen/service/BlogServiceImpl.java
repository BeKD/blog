package com.chen.service;

import com.chen.dao.BlogDao;
import com.chen.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limi on 2017/10/20.
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public List<Blog> listAllBlogs() {
        return blogDao.listAllBlogs();
    }

    @Override
    public List<Blog> listBlogs(Integer pageNum, Integer pageSize) {
        return blogDao.listBlogs(pageNum, pageSize);
    }

    @Override
    public List<Blog> listBlogsByTypeId(Integer typeId, Integer pageNum, Integer pageSize) {
        return blogDao.listBlogsByTypeId(typeId, pageNum, pageSize);
    }

    @Override
    public List<Blog> listBlogsByTagId(Integer typeId, Integer pageNum, Integer pageSize) {
        return blogDao.listBlogsByTagId(typeId, pageNum, pageSize);
    }

    @Override
    public List<Blog> listRecommendedBlogs() {
        return blogDao.listRecommendBlogs();
    }

    @Override
    public List<Blog> listNewBlogs() {
        return blogDao.listNewBlogs();
    }

    @Override
    public List<Blog> listBlogBySearch(String query) {
        //return null;
        return blogDao.listBlogBySearch(query);
    }

    @Override
    public List<Blog> listBlogBySearchTitleOrType(String title, Integer typeId) {
        return blogDao.listBlogBySearchTitleOrType(title, typeId);
    }

    @Override
    public Blog getBlogById(Integer id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setCommentCount(0);
        return blogDao.saveBlog(blog);
    }

    @Override
    public int updateBlog(Blog Blog) {
        Blog.setUpdateTime(new Date());
        return blogDao.updateBlog(Blog);
    }

    @Override
    public int updateView(int id) {
        return blogDao.updateViews(id);
    }

    @Override
    public int deleteBlogById(Integer id) {
        return blogDao.deleteBlogById(id);
    }

    @Override
    public int getBlogTotal() {
        return blogDao.getBlogTotal();
    }

    @Override
    public int getBlogViewTotal() {
        return blogDao.getBlogViewTotal();
    }

    @Override
    public int getBlogCommentTotal() {
        return blogDao.getBlogCommentTotal();
    }

    @Override
    public int getBlogMessageTotal() {
        return blogDao.getBlogMessageTotal();
    }

    @Override
    public int getMaxBlogId() {
        return blogDao.getMaxBlogId();
    }

    @Override
    public Map<String, List<Blog>> archiveBlogByYear() {
        List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogDao.findBlogsByYear(year));
        }
        return map;
    }
}
