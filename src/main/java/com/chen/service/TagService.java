package com.chen.service;

import com.chen.entity.Tag;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TagService {

    List<Tag> listTag(int pageNum, int pageSize);

    List<Tag> listAllTag();

    List<Tag> listTagByIds(String tagIds);

    Tag getTagById(Integer id);

    Tag getTagByName(String name);

    int saveTag(Tag tag);

    int updateTag(Tag tag);

    List<Integer> listTagIdsByBlogId(int blogId);

    int deleteTagById(Integer id);

    //查询tag总数
    int getTagTotal();

    //查询前num个tag
    List<Tag> listTagTop(int num);

    /**
     * 下面4个函数是为了保证标签的文章数一致性
     */
    //把出现在tagIds里的tag的文章数+1
    void addArctleCount(List<Tag> tags);

    //把出现在tagIds里的tag的文章数-1
    void reduceArctleCount(List<Tag> tags);

    //在blog_tags表中删除所有blogId的表项
    void clearTags(int blogId);

    //在blog_tags表中增加对应blogId的表项
    void addBlogTags(int blogId, List<Tag> tags);
}
