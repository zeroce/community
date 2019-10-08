package life.maijiang.community.controller;

import life.maijiang.community.dto.CommentDTO;
import life.maijiang.community.dto.ResultDTO;
import life.maijiang.community.exception.CustomizeErrorCode;
import life.maijiang.community.model.Comment;
import life.maijiang.community.model.User;
import life.maijiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 评论记录
     * @param commentDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getAccountId());
        comment.setLikeCount(0L);
        comment.setCommentedCount(1L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
