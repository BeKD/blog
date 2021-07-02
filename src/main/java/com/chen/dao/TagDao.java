package com.chen.dao;

import com.chen.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagDao {

    List<Tag> listTag(int pageNum, int pageSize);

    List<Tag> listAllTag();

    List<Tag> listTagByIds(List<Integer> ids);

    Tag getTagById(Integer id);

    Tag getTagByName(String name);

    int saveTag(Tag Tag);

    int updateTag(Tag Tag);

    int deleteTagById(int id);

    int getTagTotal();

    //按文章数量降序, 返回前 num 个标签
    List<Tag> listTagTop(int num);

    void addArticleCountByTagId(int tagId);

    void reduceArticleCountByTagId(int tagId);

    void clearTagsByBlogId(int blogId);

    void addBlogTags(int blogId, int tagId);

    List<Integer> listTagIdsByBlogId(int blogId);
}
