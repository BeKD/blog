package com.chen.service;

import com.chen.entity.Comment;

import java.util.List;

/**
 * Created by limi on 2017/10/22.
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Integer blogId);

    int saveComment(Comment comment);

//    查询子评论
//    List<Comment> getChildComment(Integer blogId,Integer id);

    //删除评论
    void deleteComment(Comment comment,Integer id);

}
