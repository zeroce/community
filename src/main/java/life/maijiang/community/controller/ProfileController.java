package life.maijiang.community.controller;

import life.maijiang.community.dto.PaginationDTO;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.User;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action", value = "") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          Model model) {
        User user = null;
        String setionName = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length != 0 ) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (null != user) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
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
