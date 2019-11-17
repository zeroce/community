package life.maijiang.community.controller;

import life.maijiang.community.dto.PaginationDTO;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(name = "search", required = false) String search,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "15") Integer size,
                         @RequestParam(name = "tag", required = false) String tag) {

        PaginationDTO pagination = questionService.list(page, size, search, tag);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchContent", search);
        model.addAttribute("hotTag", tag);
        return "search";
    }
}
