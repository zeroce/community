package life.maijiang.community.controller;

import life.maijiang.community.dto.QuestionDTO;
import life.maijiang.community.model.Question;
import life.maijiang.community.model.User;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {

        if (null == title || title.equals("")) {
            model.addAttribute("error", "标题不能为空");
            return  "publish";
        }
        if (null == description || description.equals("")) {
            model.addAttribute("error", "问题补充不能为空");
            return  "publish";
        }
        if (null == tag || tag.equals("")) {
            model.addAttribute("error", "标签不能为空");
            return  "publish";
        }

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            model.addAttribute("error", "用户未登录！");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setCreatorAccount(user.getAccountId());
        question.setId(id);

        questionService.createOrUpdate(question);
        return "redirect:/index";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        return "publish";
    }
}
