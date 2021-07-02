package com.chen.service;

import com.chen.entity.Type;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TypeService {


    List<Type> listAllType();

    List<Type> listType(int pageNum, int pageSize);

    Type getTypeById(Integer id);

    Type getTypeByName(String name);

    int saveType(Type type);

    int updateType(Type type);

    void addArticleCount(int typeId);

    void reduceArticleCount(int typeId);

    int deleteType(Integer id);

    //按文章数量降序排列, 返回最多的前 num 个分类
    List<Type> listTypeTop(int num);

    int getTypeTotal();
}
