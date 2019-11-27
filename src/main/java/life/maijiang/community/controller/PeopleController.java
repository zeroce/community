package life.maijiang.community.controller;

import life.maijiang.community.dto.QuestionAndCommentDTO;
import life.maijiang.community.enums.CommentTypeEnum;
import life.maijiang.community.exception.CustomizeErrorCode;
import life.maijiang.community.model.Comment;
import life.maijiang.community.model.Question;
import life.maijiang.community.model.User;
import life.maijiang.community.service.CommentService;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PeopleController {

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private QuestionService questionService;

    @GetMapping("/people/{username}")
    public String personal(@PathVariable(name = "username",value = "") String username,
                           HttpServletRequest request,
                           Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            model.addAttribute("message", CustomizeErrorCode.NO_LOGIN.getMessage());
            return "error";
        }
        // 回复
        // 回复问题的是1，回复评论的是2
        // 根据回复的parentId确定问题
        List<Comment> commentList = commentService.listByCommentator(user.getAccountId(), CommentTypeEnum.QUESTION.getType());
        List<QuestionAndCommentDTO> questionListWithComment = new ArrayList<>();
        commentList.forEach(x -> {
            QuestionAndCommentDTO questAndComm = new QuestionAndCommentDTO();
            questAndComm.setComment(x);
            questAndComm.setQuestion(questionService.findById(x.getParentId()));
            questionListWithComment.add(questAndComm);
        });
        // 发问
        List<Question> questionListWithUserAccount = questionService.selectByCreatorAccount(user.getAccountId());
        // 动态

        model.addAttribute("user", user);
        model.addAttribute("questAndComm", questionListWithComment);
        model.addAttribute("quesListWithUser", questionListWithUserAccount);
        return "people";
    }
}
