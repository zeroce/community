package life.maijiang.community.controller;

import life.maijiang.community.provider.HotTagsProvider;
import life.maijiang.community.dto.PaginationDTO;
import life.maijiang.community.service.QuestionService;
import life.maijiang.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagsProvider hotTagsProvider;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "15") Integer size,
                        @RequestParam(name = "sortedBy", required = false) String sortedBy) {

        PaginationDTO pagination = questionService.listBySorted(page, size, sortedBy);
        List<String> hotTagCacheHots = hotTagsProvider.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("hotTags", hotTagCacheHots);
        model.addAttribute("sortedBy", sortedBy);
        return "index";
    }
}
