package life.maijiang.community.controller;

import life.maijiang.community.dto.CommentDTO;
import life.maijiang.community.dto.QuestionDTO;
import life.maijiang.community.enums.CommentTypeEnum;
import life.maijiang.community.model.User;
import life.maijiang.community.service.CommentService;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    /**
     * 问题详情
     * @param id
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           HttpServletRequest request,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectByRelatedTag(questionDTO);
        List<CommentDTO> comments = commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);
        // 累加阅读数
        questionService.increaseView(id);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        User user = (User) request.getSession().getAttribute("user");
        if (null != user) {
            request.getSession().setAttribute("userAccountId", user.getAccountId());
        } else {
            request.getSession().setAttribute("userAccountId", null);
        }
        return "question";
    }
}
