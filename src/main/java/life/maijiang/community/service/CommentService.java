package life.maijiang.community.service;

import life.maijiang.community.dto.CommentDTO;
import life.maijiang.community.enums.CommentTypeEnum;
import life.maijiang.community.exception.CustomizeErrorCode;
import life.maijiang.community.exception.CustomizeException;
import life.maijiang.community.mapper.CommentMapper;
import life.maijiang.community.mapper.QuestionExtMapper;
import life.maijiang.community.mapper.QuestionMapper;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 评论功能
     * @param comment
     */
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMEMT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (null == question) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            commentMapper.insert(comment);
            questionExtMapper.intCommentCount(question);
        }
    }

    /**
     * 问题回复收集
     * @param id
     * @return
     */
    public List<CommentDTO> listByQuestionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        // 获取去重的评论人集合
        Set<String> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<String> userAccountIds = new ArrayList<>();
        userAccountIds.addAll(commentators);

        // 获取评论人并转化为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdIn(userAccountIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));


        // 转化 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
