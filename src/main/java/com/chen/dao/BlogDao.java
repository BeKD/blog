package com.chen.dao;

import com.chen.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogDao {

    List<Blog> listAllBlogs();

    List<Blog> listBlogs(Integer pageNum, Integer pageSize);

    Blog getBlogById(Integer id);

    List<Blog> listBlogsByTypeId(Integer typeId, Integer pageNum, Integer pageSize);

    List<Blog> listBlogsByTagId(Integer tagId, Integer pageNum, Integer pageSize);

    List<Blog> listRecommendBlogs();

    List<Blog> listNewBlogs();

    //前台的搜索 by title, content
    List<Blog> listBlogBySearch(String query);

    //管理员后台搜索 by title, type
    List<Blog> listBlogBySearchTitleOrType(String title, Integer typeId);


    int saveBlog(Blog blog);

    int updateBlog(Blog Blog);

    int deleteBlogById(int id);

    int getBlogTotal();

    int getBlogViewTotal();

    int getBlogCommentTotal();

    int getBlogMessageTotal();

    int updateViews(int id);

    // 根据博客id查询出评论数量
    int getCommentCountById(Integer id);

    int getMaxBlogId();

    //查询所有blog的涉及年份
    List<String> findGroupYear();

    //查询所有博客 by year
    List<Blog> findBlogsByYear(String year);

}
