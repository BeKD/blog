package com.chen.service;

import com.chen.entity.Blog;

import java.util.List;
import java.util.Map;

/**
 * Created by limi on 2017/10/20.
 */
public interface BlogService {

    List<Blog> listAllBlogs();

    List<Blog> listBlogs(Integer pageNum, Integer pageSize);

    //根据TypeId获取博客列表，在分类页进行的操作
    List<Blog> listBlogsByTypeId(Integer typeId, Integer pageNum, Integer pageSize);

    List<Blog> listBlogsByTagId(Integer typeId, Integer pageNum, Integer pageSize);

    List<Blog> listBlogBySearch(String content);

    List<Blog> listBlogBySearchTitleOrType(String title, Integer typeId);

    List<Blog> listRecommendedBlogs();

    // 最新博客列表
    List<Blog> listNewBlogs();

    Blog getBlogById(Integer id);

    int saveBlog(Blog blog);

    int updateBlog(Blog Blog);

    int updateView(int id);

    int deleteBlogById(Integer id);

    int getBlogTotal();

    int getBlogViewTotal();

    int getBlogCommentTotal();

    int getBlogMessageTotal();

    //获取最大的blog id(刚刚插入的)
    int getMaxBlogId();

    //把blog按年份归档
    Map<String,List<Blog>> archiveBlogByYear();

}
