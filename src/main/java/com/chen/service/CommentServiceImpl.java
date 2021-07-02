package com.chen.service;

import com.chen.dao.BlogDao;
import com.chen.dao.CommentDao;
import com.chen.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limi on 2017/10/22.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBlogId(Integer blogId) {
        //查询出所有一级评论(无父级评论)
        List<Comment> comments = commentDao.findByBlogIdParentId(blogId, Integer.valueOf("-1"));
        for(Comment comment : comments){
            Integer id = comment.getId();
            String pNickname = comment.getNickname();
            //     查询出子评论
            List<Comment> childComments = commentDao.findByBlogIdParentId(blogId, id);
            //      把子评论添加到父评论的下级
            combineChildren(blogId, childComments, pNickname);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(Integer blogId, List<Comment> childComments, String parentNickname) {
        if(childComments.size() > 0){
            for(Comment childComment : childComments){
                String childNickname = childComment.getNickname();
                // 设置子一级评论的父评论
                childComment.setParentNickname(parentNickname);
                tempReplys.add(childComment);
                Integer childId = childComment.getId();
                // 递归查询出所有子二级评论
                recursively(blogId, childId, childNickname);
            }
        }
    }

    private void recursively(Integer blogId, Integer parentId, String parentNickname1) {

        //   根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentDao.findByBlogIdParentId(blogId, parentId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Integer replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }

    //    新增评论
    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        int comments = commentDao.saveComment(comment);
//        文章评论计数
        blogDao.getCommentCountById(comment.getBlogId());
        return comments;
    }

    //    删除评论
    @Override
    public void deleteComment(Comment comment,Integer id) {
        commentDao.deleteComment(id);
        blogDao.getCommentCountById(comment.getBlogId());
    }
}
