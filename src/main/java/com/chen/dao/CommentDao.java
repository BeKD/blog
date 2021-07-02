package com.chen.dao;

import com.chen.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {

    /**
     * 查询父评论下的所有子评论
     * @param blogId
     * @param parentId 父级评论的id
     * @return
     */
    List<Comment> findByBlogIdParentId(@Param("blogId") Integer blogId, @Param("parentId") Integer parentId);

    //添加一个评论
    int saveComment(Comment comment);

    //删除评论
    void deleteComment(Integer id);


}
