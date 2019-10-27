package life.maijiang.community.controller;

import life.maijiang.community.dto.CommentCreateDTO;
import life.maijiang.community.dto.CommentDTO;
import life.maijiang.community.dto.ResultDTO;
import life.maijiang.community.enums.CommentTypeEnum;
import life.maijiang.community.exception.CustomizeErrorCode;
import life.maijiang.community.model.Comment;
import life.maijiang.community.model.User;
import life.maijiang.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 问题一级评论记录
     * @param commentCreateDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (null == commentCreateDTO || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMEMT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getAccountId());
        comment.setLikeCount(0L);
        comment.setCommentedCount(0L);
        commentService.insert(comment, user);
        return ResultDTO.okOf();
    }

    /**
     * 问题二级评论记录
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO secondComments(@PathVariable(name = "id") Long id,
                                    HttpServletRequest request) {
        List<CommentDTO> commentDTOS = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
