package life.maijiang.community.controller;

import life.maijiang.community.dto.QuestionDTO;
import life.maijiang.community.model.User;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 问题详情
     * @param id
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           HttpServletRequest request,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question", questionDTO);
        User user = (User) request.getSession().getAttribute("user");
        if (null != user) {
            request.getSession().setAttribute("userAccountId", user.getAccountId());
        } else {
            request.getSession().setAttribute("userAccountId", null);
        }
        // 累加阅读数
        questionService.increaseView(id);

        return "question";
    }
}
