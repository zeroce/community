package life.maijiang.community.controller;

import life.maijiang.community.cache.HotTagCache;
import life.maijiang.community.dto.PaginationDTO;
import life.maijiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "15") Integer size) {

        PaginationDTO pagination = questionService.list(page, size);
        List<String> hotTagCacheHots = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("hotTags", hotTagCacheHots);
        return "index";
    }
}
