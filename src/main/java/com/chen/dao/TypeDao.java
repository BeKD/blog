package com.chen.dao;

import com.chen.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeDao {

    List<Type> listType(int pageNum, int pageSize);

    List<Type> listAllType();

    List<Type> getAllTypeAndBlog();

    Type getTypeById(Integer id);

    Type getTypeByName(String name);

    int saveType(Type type);

    int updateType(Type type);

    int deleteType(Integer id);

    // 返回前num个分类(按文章数量降序
    List<Type> listTypeTop(int num);

    // 返回分类总个数
    int getTypeTotal();

    void addArticleCount(int typeId);

    void reduceArticleCount(int typeId);

}
