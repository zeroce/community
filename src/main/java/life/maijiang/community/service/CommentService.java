package life.maijiang.community.service;

import life.maijiang.community.dto.CommentDTO;
import life.maijiang.community.enums.CommentTypeEnum;
import life.maijiang.community.model.Comment;
import life.maijiang.community.model.User;

import java.util.List;

public interface CommentService {
    /**
     * 评论功能
     * @param comment
     * @param commentator
     */
    public void insert(Comment comment, User commentator);
    /**
     * 问题回复收集
     * @param id
     * @param type
     * @return
     */
    public List<CommentDTO> listByQuestionId(Long id, CommentTypeEnum type);
    /**
     * 个人回复收集（问题）
     * @param commentator
     * @return
     */
    public List<Comment> listByCommentator(String commentator, Integer type);
}
