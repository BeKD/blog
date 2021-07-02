package com.chen.service;

import com.chen.dao.TypeDao;
import com.chen.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
@Service
public class TypeServiceImpl implements TypeService {


    @Autowired
    private TypeDao typeDao;


    @Override
    public List<Type> listAllType() {
        return typeDao.listAllType();
    }

    @Transactional
    @Override
    public List<Type> listType(int pageNum, int pageSize) {
        return typeDao.listType(pageNum, pageSize);
    }

    @Transactional
    @Override
    public Type getTypeById(Integer id) {
        return typeDao.getTypeById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Override
    public void addArticleCount(int typeId) {
        typeDao.addArticleCount(typeId);
    }

    @Override
    public void reduceArticleCount(int typeId) {
        typeDao.reduceArticleCount(typeId);
    }

    @Transactional
    @Override
    public int deleteType(Integer id) {
        return typeDao.deleteType(id);
    }


    //按文章数量降序排列, 返回最多的前 num 个分类
    @Override
    public List<Type> listTypeTop(int num) {
        return typeDao.listTypeTop(num);
    }

    @Override
    public int getTypeTotal() {
        return typeDao.getTypeTotal();
    }
}
