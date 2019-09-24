package life.maijiang.community.controller;

import life.maijiang.community.dto.PaginationDTO;
import life.maijiang.community.model.User;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action", value = "") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          Model model) {
        String setionName = null;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/index";
        }
        if (action.equals("questions")) {
            setionName = "我的问题";
        } else if (action.equals("replies")) {
            setionName = "最新回复";
        } else if (action.equals("feet")) {
            setionName = "我的足迹";
        } else {
            setionName = "已收藏的帖子";
        }
        model.addAttribute("section", action);
        model.addAttribute("sectionName", setionName);

        PaginationDTO paginationDTO = questionService.list(user.getAccountId(), page, size);
        model.addAttribute("pagination", paginationDTO);

        return "profile";
    }
}
