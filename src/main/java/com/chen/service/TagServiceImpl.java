package com.chen.service;

import com.chen.dao.TagDao;
import com.chen.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;


    @Override
    public List<Tag> listTag(int pageNum, int pageSize) {
        return tagDao.listTag(pageNum, pageSize);
    }

    @Override
    public List<Tag> listAllTag() {
        return tagDao.listAllTag();
    }

    @Override
    public List<Tag> listTagByIds(String tagIds) { //1,2,3
        return tagDao.listTagByIds(convertToList(tagIds));
    }
    private List<Integer> convertToList(String ids) {
        List<Integer> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(Integer.valueOf(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag getTagById(Integer id) {
        return tagDao.getTagById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public int getTagTotal() {
        return tagDao.getTagTotal();
    }

    @Override
    public List<Tag> listTagTop(int num) {
        return tagDao.listTagTop(num);
    }


    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Override
    public void addArctleCount(List<Tag> tags) {
        for(Tag tag : tags){
            tagDao.addArticleCountByTagId(tag.getId());
        }
    }

    @Override
    public void reduceArctleCount(List<Tag> tags) {
        for(Tag tag : tags){
            tagDao.reduceArticleCountByTagId(tag.getId());
        }
    }

    @Override
    public void clearTags(int blogId) {
        tagDao.clearTagsByBlogId(blogId);
    }

    @Override
    public void addBlogTags(int blogId, List<Tag> tags) {
        for(Tag tag : tags){
            tagDao.addBlogTags(blogId, tag.getId());
        }
    }

    @Override
    public List<Integer> listTagIdsByBlogId(int blogId) {
        return tagDao.listTagIdsByBlogId(blogId);
    }

    @Transactional
    @Override
    public int deleteTagById(Integer id) {
        return tagDao.deleteTagById(id);
    }

}
