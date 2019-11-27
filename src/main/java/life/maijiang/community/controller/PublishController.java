package life.maijiang.community.controller;

import life.maijiang.community.dto.QuestionDTO;
import life.maijiang.community.dto.TagDTO;
import life.maijiang.community.model.Question;
import life.maijiang.community.model.Tag;
import life.maijiang.community.model.User;
import life.maijiang.community.service.PublishService;
import life.maijiang.community.service.QuestionService;
import life.maijiang.community.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PublishService publishService;

    @Autowired
    private TagService tagService;

    @GetMapping("/publish")
    public String publish(Model model) {
        List<TagDTO<Tag>> tagDTOS = publishService.getAllTags();
        model.addAttribute("tags", tagDTOS);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(value = "id", required = false) Long id,
                            HttpServletRequest request,
                            Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            model.addAttribute("error", "用户未登录！");
            return "publish";
        }
        if (null == description || description.equals("")) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        String isValid = tagService.filterIsValid(tag);
        if (StringUtils.isNotBlank(isValid)) {
            model.addAttribute("error", "输入非法标签" + isValid);
            return "publish";
        }
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

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
        List<TagDTO<Tag>> tagDTOList = publishService.getAllTags();
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        model.addAttribute("tags", tagDTOList);
        return "publish";
    }
}
